package com.censoft.userregister.service.impl;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement;
import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.common.utils.DateUtils;
import com.censoft.common.utils.MD5Format;
import com.censoft.userregister.domain.NationalityInfo;
import com.censoft.userregister.util.ResultUtil;
import com.censoft.userregister.dao.PersonalUserInfoRegisterMapper;
import com.censoft.userregister.dao.PersonalUserLoginRegisterMapper;
import com.censoft.userregister.domain.PersonalUserInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.service.PersonalUserInfoRegisterService;
import com.censoft.userregister.util.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Resource;
import net.bytebuddy.implementation.bytecode.Throw;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @创建人:wqgeng
 * @创建时间:2020-11-1620:24
 * @描述:sdfsdf
 */
@Service
@Transactional
public class PersonalUserInfoRegisterImpl implements PersonalUserInfoRegisterService {

    @Resource
    private PersonalUserInfoRegisterMapper personalUserInfoRegisterMapper;
    @Resource
    private PersonalUserLoginRegisterMapper personalUserLoginRegisterMapper;
    @Autowired
    private UserInterfaceServer userInterfaceServer;

    /**
     * @Author gengweiquan
     * @Description 进行用户信息的添加
     * @Date 2020/11/17 10:56
     * @Param [personalUserLogin]
     * @returns com.censoft.userregister.domain.PersonalUserLogins
     **/
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultUtil insert(PersonalUserInfo personalUserInfo) {
        int i = 0;
        try {
            //对用户新信息进行验证 后台校验使用注解的方式代替  可以注掉
            //checkOnInsert(personalUserInfo);
            int row = 0;
            personalUserInfo.setUserId(UUID.randomUUID().toString().replace("-", ""));
            personalUserInfo.setCreateTime(DateUtils.getNowDate());
            //通过身份证获取出生日期  以及 性别
            if (personalUserInfo.getCardType() == "111"){
                String[] birthAndSexByIdNo = commonUtil.getBirthAndSexByIdNo(personalUserInfo.getCardNo());
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                personalUserInfo.setDateOfBirth(sf.parse(birthAndSexByIdNo[0]));
                personalUserInfo.setGender(birthAndSexByIdNo[1]);
            }else {
                personalUserInfo.setGender("9");
            }
            //添加实名认证
            boolean b = userInterfaceServer.certTwoData(personalUserInfo.getUserName(), personalUserInfo.getCardNo());
            if (b) {
                personalUserInfo.setAuthlevel("3");
                personalUserInfo.setIsLabourReal("0");
            } else {
                personalUserInfo.setAuthlevel("2");
                personalUserInfo.setIsLabourReal("1");
            }
            row = personalUserInfoRegisterMapper.insertPersonalUserInfo(personalUserInfo);
            if (row <= 0) {
                return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
            }
            //对密码进行加密操作
            preInsert(personalUserInfo);
            PersonalUserLogin personalUserLogin = personalUserInfo.getPersonalUserLogin();
            //验证是否为手机号注册
            if (("1").equals(personalUserInfo.getPeFlag())) {
                personalUserLogin.setId(UUID.randomUUID().toString().replace("-", ""));
                personalUserLogin.setUserId(personalUserInfo.getUserId());
                personalUserLogin.setPhone(personalUserInfo.getPhone());
                personalUserLogin.setPhoneIscheck(Long.valueOf(1));
                personalUserLogin.setEmail(personalUserInfo.getEmail());
                personalUserLogin.setEmailIscheck(Long.valueOf(0));
                personalUserLogin.setIdnumber(personalUserInfo.getCardNo());
                personalUserLogin.setCreateTime(new Date());
                personalUserLogin.setCreateBy(personalUserInfo.getUserName());
                personalUserLogin.setPassword(personalUserInfo.getPassword());
            } else {
                personalUserLogin.setId(UUID.randomUUID().toString().replace("-", ""));
                personalUserLogin.setUserId(personalUserInfo.getUserId());
                personalUserLogin.setEmail(personalUserInfo.getEmail());
                personalUserLogin.setEmailIscheck(Long.valueOf(1));
                personalUserLogin.setPhone(personalUserInfo.getPhone());
                personalUserLogin.setPhoneIscheck(Long.valueOf(0));
                personalUserLogin.setIdnumber(personalUserInfo.getCardNo());
                personalUserLogin.setCreateBy(personalUserInfo.getUserName());
                personalUserLogin.setCreateTime(new Date());
                personalUserLogin.setPassword(personalUserInfo.getPassword());
            }
            i = personalUserLoginRegisterMapper.insertPersonalUserLogins(personalUserLogin);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultUtil.result("界面出错，请您刷新界面后重试");
        }
        if (i < 0) {
            return ResultUtil.result(ResultStatusEnum.S_10004.getStatus(), ResultStatusEnum.S_10004.getMassage());
        }
        return ResultUtil.result(ResultStatusEnum.S_10005.getStatus(), ResultStatusEnum.S_10005.getMassage());
        //验证密码的位数 --后续可以加
        // _checkPasswordRequirment(personalUserInfo.getPersonalUserLogins().get(0).getPassword());
    }

    //验证手机号的唯一性
    @Override
    public ResultUtil countByPhone(PersonalUserLogin personalUserLogin) {
        int i = personalUserInfoRegisterMapper.countByPhone(personalUserLogin.getPhone());
        if (i > 0) {
            return ResultUtil.result(ResultStatusEnum.S_10001.getStatus(), ResultStatusEnum.S_10001.getMassage());
        }
        return ResultUtil.success();
    }

    //验证身份证的唯一性
    @Override
    public ResultUtil countByCardNo(PersonalUserLogin personalUserLogin) {
        int i = personalUserInfoRegisterMapper.countByCardNo(personalUserLogin.getIdnumber());
        if (i > 0) {
            return ResultUtil.result(ResultStatusEnum.S_10002.getStatus(), ResultStatusEnum.S_10002.getMassage());
        }
        return ResultUtil.success();
    }

    // 验证邮箱的唯一性
    @Override
    public ResultUtil countByEmail(PersonalUserLogin personalUserLogin) {
        int i = personalUserInfoRegisterMapper.countByEmail(personalUserLogin.getEmail());
        if (i > 0) {
            return ResultUtil.result(ResultStatusEnum.S_10003.getStatus(), ResultStatusEnum.S_10003.getMassage());
        }
        return ResultUtil.success();
    }

    @Override
    public ResultUtil queryCardType() {
        List<Map<String, String>> CardTypeList = personalUserInfoRegisterMapper.queryCardType();
        return ResultUtil.success(CardTypeList);
    }

    //注册手机号时 判断填写的邮箱是否被绑定了
    @Override
    public ResultUtil countByPhoneEmail(PersonalUserLogin personalUserLogin) {
        int row = 0;
        if (StringUtils.isNotBlank(personalUserLogin.getEmail())) {
            row = personalUserLoginRegisterMapper.countByPhoneEmail(personalUserLogin.getEmail());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10014.getStatus(), ResultStatusEnum.S_10014.getMassage());
        }
        if (row > 0) {
            return ResultUtil.result(ResultStatusEnum.S_10012.getStatus(), ResultStatusEnum.S_10012.getMassage());
        }
        return ResultUtil.success();
    }

    // 邮箱注册是 判断填写的手机号是否被绑定了
    @Override
    public ResultUtil countByEmailPhone(PersonalUserLogin personalUserLogin) {
        int row = 0;
        if (StringUtils.isNotBlank(personalUserLogin.getPhone())) {
            row = personalUserLoginRegisterMapper.countByEmailPhone(personalUserLogin.getPhone());
        } else {
            return ResultUtil.result(ResultStatusEnum.S_10014.getStatus(), ResultStatusEnum.S_10014.getMassage());
        }
        if (row > 0) {
            return ResultUtil.result(ResultStatusEnum.S_10013.getStatus(), ResultStatusEnum.S_10013.getMassage());
        }
        return ResultUtil.success();
    }

    /*
     * @Author wqgeng
     * @Description 查询国籍信息
     * @Date  2020/11/19 16:47
     * @Param [nationalityInfo]
     * @returns com.censoft.userregister.util.ResultUtils
     **/
    @Override
    public ResultUtil queryNationality() {
        List<Map<String, String>> nationalityInfoList = personalUserInfoRegisterMapper.queryNationality();
        return ResultUtil.success(nationalityInfoList);
    }

    @Override
    public int countBySomeThing(PersonalUserInfo personalUserInfo) {
        return personalUserInfoRegisterMapper.countBySomeThing(personalUserInfo);
    }


    protected void preInsert(PersonalUserInfo personalUserInfo) {

        personalUserInfo.setCreateTime(new Date());
        //创建人为他本人
        personalUserInfo.setCreateBy(personalUserInfo.getUserName());
        //将密码做MD5加密处理
        String password = MD5Format.MD5(personalUserInfo.getPassword());
        personalUserInfo.setPassword(password);
    }

}
