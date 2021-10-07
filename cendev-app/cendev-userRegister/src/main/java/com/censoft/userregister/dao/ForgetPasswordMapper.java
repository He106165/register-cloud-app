package com.censoft.userregister.dao;

import com.censoft.userregister.domain.FindPersonalInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.util.ResultUtil;
import org.apache.ibatis.annotations.Mapper;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-0213:34
 *@描述:忘记密码dao
 */
@Mapper
public interface ForgetPasswordMapper {

    int updatePassword(PersonalUserLogin personalUserLogin);

    int updateEmailPassword(PersonalUserLogin personalUserLogin);

    int  saveArtificialInfo(FindPersonalInfo findPersonalInfo);

    boolean selectNameNoInfo(FindPersonalInfo findPersonalInfo);

    PersonalUserLogin selectLoginInfo(String phone);

    PersonalUserLogin selectLoginInfoByEmail(String email);

    boolean selectFindInfo(FindPersonalInfo findPersonalInfo);
}
