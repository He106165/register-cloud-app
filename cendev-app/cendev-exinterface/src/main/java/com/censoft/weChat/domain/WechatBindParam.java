package com.censoft.weChat.domain;

import lombok.Data;

/**
 *@创建人:wqgeng
 *@创建时间:2020-12-1410:17
 *@描述:微信绑定用户传参
 */
@Data
public class WechatBindParam {
private String unionId;
private String name;
private String phone;
private String cardNo ;
private String email;
private String userType;
private String password;
private String unifiedsocialcreditcode;
private String hostPersonEmail;
private String verifyType;
private String wechatUserName;
}
