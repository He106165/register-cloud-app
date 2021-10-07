package com.censoft.userregister.dao;

import com.censoft.userregister.domain.PersonalUserLogin;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-1720:21
 *@描述:注册信息
 */
@Mapper
public interface PersonalUserLoginRegisterMapper {

    int insertPersonalUserLogins(PersonalUserLogin personalUserLogin);

    int countByPhoneEmail(@Param("email") String email);

    int countByEmailPhone(@Param("phone")String phone);
}
