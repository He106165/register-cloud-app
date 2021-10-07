package com.censoft.userregister.service.impl;

import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.DateUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.userregister.dao.OrgenInfoRegisterMapper;
import com.censoft.userregister.dao.OrgenLoginRegisterMapper;
import com.censoft.userregister.domain.OrgenUserInfo;
import com.censoft.userregister.domain.OrgenUserLogin;
import com.censoft.userregister.service.OrgenInfoRegisterService;
import com.censoft.userregister.util.DesNewUtils;
import com.censoft.userregister.util.ResultStatusEnum;
import com.censoft.userregister.util.ResultUtil;
import com.netflix.discovery.converters.Auto;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-2111:08
 * @描述:机构信息注册
 */
@Service
@Transactional
public class OrgenInfoRegisterServiceImpl implements OrgenInfoRegisterService {
    @Resource
    private OrgenInfoRegisterMapper orgenInfoRegisterMapper;
    @Resource
    private OrgenLoginRegisterMapper orgenLoginRegisterMapper;
    @Autowired
    private UserInterfaceServer userInterfaceServer;
    @Autowired
    private RedisUtils redisUtils;
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil addSave(OrgenUserInfo orgenUserInfo) {

        int row = 0;
        int i = 0;
        try {
            orgenUserInfo.setOrgUserId(UUID.randomUUID().toString().replace("-", ""));
            orgenUserInfo.setCreateTime(DateUtils.getNowDate());
            //添加实名认证
            boolean b = userInterfaceServer.certTwoData(orgenUserInfo.getLegalName(), orgenUserInfo.getLegalIdnumber());
            if (b) {
                orgenUserInfo.setAuthlevel("3");
            } else {
                orgenUserInfo.setAuthlevel("2");
            }
            if ("schUnion".equals(orgenUserInfo.getOrgenFlag())) {
                orgenUserInfo.setIspass(0L);
            }
            row = orgenInfoRegisterMapper.addSaveOrgenUserInfo(orgenUserInfo);
            if (row <= 0) {
                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
            }
            OrgenUserLogin orgenUserLogin = orgenUserInfo.getOrgenUserLogin();
            orgenUserLogin.setId(UUID.randomUUID().toString().replace("-", ""));
            orgenUserLogin.setOegUserId(orgenUserInfo.getOrgUserId());
            orgenUserLogin.setHostpersionEmail(orgenUserInfo.getHostpersionEmail());
            orgenUserLogin.setStatus("00");
            //判断是国内机构注册  还是 海外机构/海外学联 注册
            if (!"inland".equals(orgenUserInfo.getOrgenFlag())) {
                orgenUserLogin.setDepermentCode("01");
            }
            if ("schUnion".equals(orgenUserInfo.getOrgenFlag())) {
                orgenUserInfo.getOrgenUserLogin().setStatus("01");
            }
            orgenUserLogin.setCreateTime(new Date());
            String password = MD5Format.MD5(orgenUserInfo.getPassword());
            orgenUserInfo.setPassword(password);
            orgenUserLogin.setPassword(orgenUserInfo.getPassword());
            i = orgenLoginRegisterMapper.addSaveOrgenUserInfo(orgenUserLogin);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.result("界面出错，请您刷新界面后重试");
        }
        if (i < 0) {
            return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
        }
        return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());
    }

    //查询法人类型
    @Override
    public ResultUtil queryPerManType() {
        List<Map<String, String>> perManTypeList = orgenInfoRegisterMapper.queryPerManType();
        return ResultUtil.success(perManTypeList);
    }

    //查询法人性质
    @Override
    public ResultUtil queryperManLine() {
        List<Map<String, String>> perManLineList = orgenInfoRegisterMapper.queryperManLine();
        return ResultUtil.success(perManLineList);
    }

    //查询部门类型
    @Override
    public ResultUtil queryDepermentCode() {
        List<Map<String, String>> depermentCodeList = orgenInfoRegisterMapper.queryDepermentCode();
        return ResultUtil.success(depermentCodeList);
    }

    //查询使领馆教育处
    @Override
    public ResultUtil queryConsulateInfo() {
        List<Map<String, String>> consulateInfoList = orgenInfoRegisterMapper.queryConsulateInfo();
        return ResultUtil.success(consulateInfoList);
    }

    //国内企业，验证（社会信用码+部门类型）唯一性
    @Override
    public ResultUtil valiIdType(OrgenUserInfo orgenUserInfo) {
        int i = orgenLoginRegisterMapper.valiIdType(orgenUserInfo.getOrgenUserLogin().getUnifiedsocialcreditcode(), orgenUserInfo.getOrgenUserLogin().getDepermentCode());
        if (i > 0) {
            return ResultUtil.result(ResultStatusEnum.S_10015.getStatus(), ResultStatusEnum.S_10015.getMassage());
        }
        return ResultUtil.success();
    }

    // 机构用户 注册 校验邮箱的唯一性
    @Override
    public ResultUtil orgenEmailCount(OrgenUserLogin orgenUserLogin) {
        int i = orgenLoginRegisterMapper.orgenEmailCount(orgenUserLogin.getHostpersionEmail());
        if (i > 0) {
            return ResultUtil.result(ResultStatusEnum.S_10024.getStatus(), ResultStatusEnum.S_10024.getMassage());
        }
        return ResultUtil.success();
    }

    //机构用户 找回密码 校验邮箱的唯一性
    @Override
    public ResultUtil orgenEmailPassCount(OrgenUserLogin orgenUserLogin) {
        int i = orgenLoginRegisterMapper.orgenEmailPassCount(orgenUserLogin.getHostpersionEmail());
        if (i == 0) {
            return ResultUtil.result(ResultStatusEnum.S_10019.getStatus(), ResultStatusEnum.S_10019.getMassage());
        }
        return ResultUtil.success();
    }

    //机构用户找回密码 进行修改
    @Override
    public ResultUtil updateOrgenEmailPassword(OrgenUserLogin orgenUserLogin) {
        int row = 0;
        if (StringUtils.isNotBlank(orgenUserLogin.getHostpersionEmail())) {
            //对填写的密码进行加密处理
            String password = MD5Format.MD5(orgenUserLogin.getPassword());
            orgenUserLogin.setPassword(password);
            orgenUserLogin.setUpdateBy(orgenUserLogin.getHostpersionEmail());
            orgenUserLogin.setUpdateTime(new Date());
            row = orgenLoginRegisterMapper.updateOrgenEmailPassword(orgenUserLogin);
            //忘记密码功能 修改密码成功后 将redis中的有关信息进行删除
            if (row > 0) {
                if (StringUtils.isNotBlank(orgenUserLogin.getHostpersionEmail())){
                    //国内用户
                    String inlandkey = new StringBuilder(orgenUserLogin.getHostpersionEmail()).append("##01").toString();
                    //查询邮箱机构用户的社会信用码
                    redisUtils.delete(inlandkey);
                    //根据用户填写的邮箱查询登录表中是否含有社会信用码
                    //如果含有社会信用码 将redis中社会信用码的信息删除
                    String Unifiedsocialcreditcode = orgenLoginRegisterMapper.selectUnifiedsocialcreditcode(orgenUserLogin.getHostpersionEmail());
                    if (StringUtils.isNotBlank(Unifiedsocialcreditcode)){
                        String ukey = new StringBuilder(Unifiedsocialcreditcode).append("##01").toString();
                        redisUtils.delete(ukey);
                    }
                }
            }
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10000.getStatus(), ResultStatusEnum.S_10000.getMassage());
        }

        if (row <= 0) {
            return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
        }
        return ResultUtil.result(ResultStatusEnum.S_10017.getStatus(), ResultStatusEnum.S_10017.getMassage());
    }


}
