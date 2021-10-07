package com.censoft.userregister.service.impl;

import com.censoft.common.redis.util.RedisUtils;
import com.censoft.common.utils.DateUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.userregister.dao.ForgetPasswordMapper;
import com.censoft.userregister.dao.PersonalUserInfoRegisterMapper;
import com.censoft.userregister.dao.PersonalUserLoginRegisterMapper;
import com.censoft.userregister.domain.FindPersonalInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.service.ForgetPasswordService;
import com.censoft.userregister.util.DesNewUtils;
import com.censoft.userregister.util.ResultStatusEnum;
import com.censoft.userregister.util.ResultUtil;
import com.netflix.discovery.converters.Auto;
import java.util.Date;
import java.util.UUID;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人:wqgeng
 * @创建时间:2020-12-0213:33
 * @描述:忘记密码实现类
 */

@Service
@Transactional
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
    @Resource
    private ForgetPasswordMapper forgetPasswordMapper;
    @Resource
    private PersonalUserInfoRegisterMapper personalUserInfoRegisterMapper;
    @Resource
    private PersonalUserLoginRegisterMapper personalUserLoginRegisterMapper;
    @Autowired
    private RedisUtils redisUtils;
    //设置redis前缀
    private static final String wechat_redis_key = "wechat_";
    //设置redis前缀
    private static final String Alipay_redis_key = "#Alipay_";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil updatePassword(PersonalUserLogin personalUserLogin) {
        try {
            int row = 0;
            // 邮箱和手机号不能同时进行修改   邮箱和手机号也不能同时为空
            if (StringUtils.isNotBlank(personalUserLogin.getPhone()) && StringUtils.isNotBlank(personalUserLogin.getEmail())) {
                return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
            } else if (StringUtils.isBlank(personalUserLogin.getPhone()) && StringUtils.isBlank(personalUserLogin.getEmail())) {
                return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
            }
            if (StringUtils.isNotBlank(personalUserLogin.getPhone())) {
                //对填写的密码进行加密处理
                String password = MD5Format.MD5(personalUserLogin.getPassword());
                personalUserLogin.setPassword(password);
                personalUserLogin.setUpdateBy(personalUserLogin.getPhone());
                personalUserLogin.setUpdateTime(new Date());
                PersonalUserLogin p = forgetPasswordMapper.selectLoginInfo(personalUserLogin.getPhone());
                if (p == null){
                    return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
                }
                    row = forgetPasswordMapper.updatePassword(personalUserLogin);
                //根据前端传的手机号查询登录表信息，用于删除redis的缓存登录信息
                String wechatKey = new StringBuilder(wechat_redis_key).append(p.getWecharId()).toString();
                //redis中支付宝扫码登录的key
                String AlipaytKey = new StringBuilder(Alipay_redis_key).append(p.getAlipayId()).toString();
                //修改密码完成后，删除redis里边的所有登录信息
                if (row > 0) {
                    if (StringUtils.isNotBlank(p.getPhone())) {
                        redisUtils.delete(p.getPhone());
                    }
                    if (StringUtils.isNotBlank(p.getEmail())) {
                        redisUtils.delete(p.getEmail());
                    }
                    if (StringUtils.isNotBlank(p.getIdnumber())) {
                        redisUtils.delete(p.getIdnumber());
                    }
                    if (StringUtils.isNotBlank(p.getName())) {
                        redisUtils.delete(p.getName());
                    }
                    if (StringUtils.isNotBlank(p.getWecharId())) {
                        redisUtils.delete(wechatKey);
                    }
                    if (StringUtils.isNotBlank(p.getAlipayId())) {
                        redisUtils.delete(AlipaytKey);
                    }
                }
            } else if (StringUtils.isNotBlank(personalUserLogin.getEmail())) {
                //对填写的密码进行加密处理
                String password = MD5Format.MD5(personalUserLogin.getPassword());
                personalUserLogin.setPassword(password);
                personalUserLogin.setUpdateBy(personalUserLogin.getEmail());

                personalUserLogin.setUpdateTime(new Date());
                PersonalUserLogin m = forgetPasswordMapper.selectLoginInfoByEmail(personalUserLogin.getEmail());
                if (m == null){
                    return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
                }
                //根据前端传的手机号查询登录表信息，用于删除redis的缓存登录信息
                String wechatKey = new StringBuilder(wechat_redis_key).append(m.getWecharId()).toString();
                //redis中支付宝扫码登录的key
                String AlipaytKey = new StringBuilder(Alipay_redis_key).append(m.getAlipayId()).toString();
                row = forgetPasswordMapper.updateEmailPassword(personalUserLogin);
                //修改密码完成后，删除redis里边的所有登录信息
                if (row > 0) {
                    if (StringUtils.isNotBlank(m.getPhone())) {
                        redisUtils.delete(m.getPhone());
                    }
                    if (StringUtils.isNotBlank(m.getEmail())) {
                        redisUtils.delete(m.getEmail());
                    }
                    if (StringUtils.isNotBlank(m.getIdnumber())) {
                        redisUtils.delete(m.getIdnumber());
                    }
                    if (StringUtils.isNotBlank(m.getName())) {
                        redisUtils.delete(m.getName());
                    }
                    if (StringUtils.isNotBlank(m.getWecharId())) {
                        redisUtils.delete(wechatKey);
                    }
                    if (StringUtils.isNotBlank(m.getAlipayId())) {
                        redisUtils.delete(AlipaytKey);
                    }
                }
            } else {
                return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
            }

            if (row <= 0) {
                return ResultUtil.result(ResultStatusEnum.S_10016.getStatus(), ResultStatusEnum.S_10016.getMassage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.result(ResultStatusEnum.S_10017.getStatus(), ResultStatusEnum.S_10017.getMassage());
    }

    @Override
    public ResultUtil phonePasswordCount(PersonalUserLogin personalUserLogin) {
        int i = personalUserLoginRegisterMapper.countByEmailPhone(personalUserLogin.getPhone());
        if (i == 0) {
            return ResultUtil.result(ResultStatusEnum.S_10018.getStatus(), ResultStatusEnum.S_10018.getMassage());
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil emailPasswordCount(PersonalUserLogin personalUserLogin) {
        int i = personalUserLoginRegisterMapper.countByPhoneEmail(personalUserLogin.getEmail());
        if (i == 0) {
            return ResultUtil.result(ResultStatusEnum.S_10019.getStatus(), ResultStatusEnum.S_10019.getMassage());
        }
        return ResultUtil.success();
    }

    //添加人工找回信息
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil saveArtificialInfo(FindPersonalInfo findPersonalInfo) {
        //根据姓名 证件号校验是否有该用户信息
        if (!forgetPasswordMapper.selectNameNoInfo(findPersonalInfo)) {
            return ResultUtil.result(ResultStatusEnum.S_10050.getStatus(), ResultStatusEnum.S_10050.getMassage());
        }
        if (forgetPasswordMapper.selectFindInfo(findPersonalInfo)) {
            return ResultUtil.result(ResultStatusEnum.S_10051.getStatus(), ResultStatusEnum.S_10051.getMassage());
        }
        String fileUrl = findPersonalInfo.getFileUrl();
        String substring = fileUrl.substring(fileUrl.lastIndexOf(".") + 1);
        if ("JPG".equals(substring.toUpperCase()) || "PNG".equals(substring.toUpperCase())) {
            int i = 0;
            findPersonalInfo.setId(UUID.randomUUID().toString().replace("-", ""));
            findPersonalInfo.setCreateTime(DateUtils.getNowDate());
            findPersonalInfo.setStatus("0");
            findPersonalInfo.setDelFlag(0L);
//            String password = MD5Format.MD5(findPersonalInfo.getPassword());
//            findPersonalInfo.setPassword(password);
            i = forgetPasswordMapper.saveArtificialInfo(findPersonalInfo);
            if (i < 0) {
                return ResultUtil.result(ResultStatusEnum.S_10020.getStatus(), ResultStatusEnum.S_10020.getMassage());
            }
            return ResultUtil.result(ResultStatusEnum.S_10017.getStatus(), ResultStatusEnum.S_10017.getMassage());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10052.getStatus(), ResultStatusEnum.S_10052.getMassage());
        }
    }
}

