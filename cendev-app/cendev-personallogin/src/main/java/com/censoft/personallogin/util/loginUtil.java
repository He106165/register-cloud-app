package com.censoft.personallogin.util;

import com.censoft.common.constant.Constants;
import com.censoft.common.constant.UserConstants;
import com.censoft.common.log.enums.BusinessType;
import com.censoft.common.log.enums.OperatorType;
import com.censoft.common.log.publish.PublishFactory;
import com.censoft.common.utils.MessageUtils;
import com.censoft.personallogin.domain.PersonalUserLogin;
import org.apache.commons.lang3.StringUtils;

public class loginUtil {

    /**
     * 检验用户名、密码
     * */
    public static boolean checkUserInfo(String username, String password){
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名，密码为空", BusinessType.LOGIN.toString(),null,OperatorType.PERSONAL.toString());
            return false;
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            PublishFactory.recordLogininfor(username, Constants.LOGIN_FAIL, "用户名不符合规则校验",BusinessType.LOGIN.toString(),null, OperatorType.PERSONAL.toString());
            return false;
        }
        return true;
    }

    /***
     * 登陆 获取人员信息
     * 可能存在 身份证是重复的  ，怎么处理？  取第一条么
     *
     * */
    public static PersonalUserLogin  getInfo(String username){
        //先检验用户名是啥？
        int length=username.length();

        PersonalUserLogin personalUserLogin=new PersonalUserLogin();
        //1.手机号
        if(length==11 && PhoneUtil.isChinaPhoneLegal(username)){
            personalUserLogin.setLoginType("ph");
            personalUserLogin.setPhone(username);
        }
        //2.身份证
        else if(length==18 && CheckIDUtil.isIDNumber(username)){
            personalUserLogin.setIdnumber(username);
        }
        //3.邮箱
        else if(username.indexOf("@")> 1 && CheckEmailValidityUtil.checkEmail(username)){
            personalUserLogin.setLoginType("em");
            personalUserLogin.setEmail(username);
        }
        //4.用户名
        else{
            personalUserLogin.setName(username);
        }
        return personalUserLogin;
    }
    /**
     * 重置
    **/
    public static void reset(PersonalUserLogin personalUserLogin){
        personalUserLogin.setName("");
        personalUserLogin.setEmail("");
        personalUserLogin.setIdnumber("");
        personalUserLogin.setPhone("");
    }
}
