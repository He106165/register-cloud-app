package com.censoft.otherlogin.AwxLogin.mapper;

import com.censoft.otherlogin.Alipay.domain.PersonalUserInfo;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @创建人:wqgeng
 * @创建时间:2021-02-0411:41
 * @描述:微信个人信息
 */
@Mapper
public interface WXLoginMapper {

    boolean selectWxInfo(@Param("unionid") String unionid);

    int insertWxinfo(@Param("unionid") String unionid, @Param("nickname") String nickname, @Param("sex") String sex, @Param("province") String province);

    boolean selectUserId(@Param("unionid") String unionid);

    int insertPersonInfo(Map map);

    int insertPersonLogin(Map map);

    int updateUserId(@Param("userId") String userId, @Param("id") String id);

    String queryUserId(@Param("unionid") String unionid);

    PersonalUserInfo selectWechatUserInfo(String s);

    boolean queryUnionid(@Param("unionid") String unionid);

    int updateWechatInfo(@Param("userId") String userId, @Param("unionid") String unionid);

    int updateLoginWechat(@Param("userId") String userId, @Param("unionid") String unionid);

    int insertWxsinfo(@Param("weChatUnionid") String weChatUnionid,@Param("nickName") String nickName);

    String selectNicename(@Param("weChatUnionid") String weChatUnionid);
}
