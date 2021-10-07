package com.censoft.userregister.dao;

import com.censoft.userregister.domain.FindPersonalInfo;
import com.censoft.userregister.domain.NationalityInfo;
import com.censoft.userregister.domain.PersonalUserInfo;
import com.censoft.userregister.domain.PersonalUserLogin;
import com.censoft.userregister.util.ResultUtil;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-1711:03
 *@描述:用户注册
 */
@Mapper
public interface PersonalUserInfoRegisterMapper {

    int insertPersonalUserInfo(PersonalUserInfo personalUserInfo);

    List<Map<String, String>> queryNationality();

    int countByPhone(@Param("phone") String phone);

    int countByCardNo(@Param("idnumber") String idnumber);

    int countByEmail(@Param("email")String email);

    List<Map<String,String>> queryCardType();

    /**
    * @Description 个人昵称唯一校验
    * @Parm
    * @return
    **/
    public int countBySomeThing(PersonalUserInfo personalUserInfo);
}
