package com.censoft.userregister.service;

import com.censoft.userregister.domain.NationalityInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.util.ResultUtil;
import com.censoft.userregister.domain.PersonalUserInfo;
import org.apache.ibatis.annotations.Param;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-1620:21
 *@描述:sds
 */
public interface PersonalUserInfoRegisterService {

    ResultUtil insert(PersonalUserInfo personalUserInfo);

    ResultUtil queryNationality();

    ResultUtil countByPhone(PersonalUserLogin personalUserLogin);

    ResultUtil countByCardNo(PersonalUserLogin personalUserLogin);

    ResultUtil countByEmail(PersonalUserLogin personalUserLogin);

    ResultUtil queryCardType();

    ResultUtil countByPhoneEmail(PersonalUserLogin personalUserLogin);

    ResultUtil countByEmailPhone(PersonalUserLogin personalUserLogin);

    /**
     * @Description 个人信息唯一校验
     * @Parm
     * @return
     **/
    public int countBySomeThing(PersonalUserInfo personalUserInfo);
}
