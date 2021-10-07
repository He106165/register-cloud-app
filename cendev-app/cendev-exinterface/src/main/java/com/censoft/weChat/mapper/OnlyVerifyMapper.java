package com.censoft.weChat.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-1416:24
 *@描述:微信端用来进行唯一性校验
 */
@Mapper
public interface OnlyVerifyMapper {

    int countPhone(@Param("phone") String phone);

    int countEmail(@Param("email") String email);

    int countCardNo(@Param("idNumber") String idNumber);

    int countUnifiedsocialcreditcode(@Param("unifiedsocialcreditcode") String unifiedsocialcreditcode);

    int countEmailCode(@Param("hostpersonemail") String hostpersonemail);
}
