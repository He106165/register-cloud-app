package com.censoft.otherlogin.binding.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;
import org.apache.ibatis.annotations.Param;

/**
 * 领英信息Mapper接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
@Mapper
public interface BingdingMapper
{
    /*得到当前个人用户信息*/
    Map getPersonInfo(Map map);

    /*得到当前机构用户信息*/
    Map getOrganInfo(Map map);

    /*绑定支付宝*/
    Integer bindAlipayInfo(Map map);

    /*解绑支付宝*/
    Integer delAlipayInfo(Map map);

    /*绑定微信*/
    Integer bindWeChatInfo(Map map);

    /*解绑微信*/
    Integer delWeChatInfo(Map map);

    Map checkAliUser(Map map);

    int delWechatUserLogin(Map paramMap);
}
