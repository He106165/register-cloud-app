package com.censoft.userregister.dao;

import com.censoft.userregister.domain.OrgenUserInfo;
import com.censoft.userregister.domain.OrgenUserLogin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@创建人:wqgeng
 *@创建时间:2020-11-2111:15
 *@描述:机构登录表mapper
 */
@Mapper
public interface OrgenLoginRegisterMapper {

    int addSaveOrgenUserInfo(OrgenUserLogin orgenUserLogin);

    int valiIdType(@Param("unifiedsocialcreditcode") String unifiedsocialcreditcode,@Param("depermentCode") String depermentCode);

    int updateOrgenEmailPassword(OrgenUserLogin orgenUserLogin);

    int orgenEmailPassCount(@Param("hostpersionEmail") String hostpersionEmail);

    int orgenEmailCount(@Param("hostpersionEmail") String hostpersionEmail);

    String selectUnifiedsocialcreditcode(@Param("hostpersionEmail") String hostpersionEmail);
}
