package com.censoft.userManage.findUser.service;

import java.util.Map;

/**
 * @创建人:wqgeng
 * @创建时间:2021-04-2514:21
 * @描述:人工找回密码发送短信
 */
public interface FindSendMsgService {
    boolean successMsg(Map<String, String> map);

    boolean failMsg(Map<String, String> map);
}
