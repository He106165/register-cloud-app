package com.censoft.weChat.mapper;

import com.censoft.util.PhoneUtil;
import com.censoft.weChat.domain.WechatUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-1410:23
 *@描述:微信端绑定用户mapper
 */
@Mapper
public interface WechatBindUserInfoMapper {

    int countPhone(@Param("phone") String phone);

    String queryPassword(@Param("phone") String phone);

    int countEmail(@Param("email") String email);

    String selectUserInfo(@Param("phone") String phone);



    String queryEmailPassword(@Param("email")String email);

    String selectUserInfoByEmail(@Param("email")String email);

    int countCardNo(@Param("idNumber") String idNumber);

    String queryCardNoPassword(@Param("idNumber") String idNumber);

    String selectUserInfoByCardNo(@Param("idNumber") String idNumber);

    int countUnifiedsocialcreditcode(@Param("unifiedsocialcreditcode") String unifiedsocialcreditcode);

    String queryUnifiedsocialcreditcodePassword(@Param("unifiedsocialcreditcode") String unifiedsocialcreditcode);

    String selectUnifiedUserInfo(@Param("unifiedsocialcreditcode") String unifiedsocialcreditcode);

    int countOrgenEmail(@Param("hostpersonEmail") String hostpersonEmail);

    String emailPassword(@Param("hostpersonEmail") String hostpersonEmail);

    String selectemailP(@Param("hostpersonEmail") String hostpersonEmail);

    int insertWechatBindUserInfo(WechatUserInfo wechatUserInfo);

    boolean useName(String wechatUserName);

    String queryUseNamePaaword(String name);

    String selectUserInfoByName(String name);

    boolean countUnionId(String unionId);

    boolean countUnionIdOrg(String unionId);
}
