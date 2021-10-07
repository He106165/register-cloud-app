package com.censoft.otherlogin.binding.service;

import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * 领英信息Service接口
 * 
 * @author cendev
 * @date 2020-10-21
 */
public interface BingdingService
{
    /*得到当前用户信息*/
    Map getUserInfo(Map map, String userType);

    /*绑定支付宝*/
    Integer bindAlipayInfo(Map map);

    /*解绑支付宝*/
    Integer delAlipayInfo(Map map);

    /*绑定微信*/
    Integer bindWeChatInfo(Map map);

    /*解绑微信*/
    String delWeChatInfo(Map paramMap,HttpServletResponse response);




}
