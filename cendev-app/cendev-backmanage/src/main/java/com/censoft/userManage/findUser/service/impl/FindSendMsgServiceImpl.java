package com.censoft.userManage.findUser.service.impl;

import com.censoft.cendevbackmanage.feign.RemoteMsgSendService;
import com.censoft.userManage.findUser.service.FindSendMsgService;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

/**
 * @创建人:wqgeng
 * @创建时间:2021-04-2514:22
 * @描述:
 */
@Service
public class FindSendMsgServiceImpl implements FindSendMsgService {
    @Autowired
    private RemoteMsgSendService remoteMsgSendService;

    @Override
    public boolean successMsg(Map<String, String> map) {
        if (StringUtils.isNotBlank(map.get("mobile")) && StringUtils.isNotBlank(map.get("email"))) {
            map.put("sendType", "1");
            map.put("subjectTitle", "统一用户注册系统信息发送");
            map.put("phoneTo", map.get("mobile"));
            map.put("phoneContent", "您好，您在教育部留学服务中心统一用户身份认证系统中提交的人工找回密码申请审核通过，您可使用手机号码或电子邮箱验证方式正常找回密码。【教留服】");
            map.put("mailTo", map.get("email"));
            map.put("emailContent", "您好，您在教育部留学服务中心统一用户身份认证系统中提交的人工找回密码申请审核通过，您可使用手机号码或电子邮箱验证方式正常找回密码。教育部留学服务中心");
        }
        //发送短信参数
        return remoteMsgSendService.SendMsg(map);
    }

    @Override
    public boolean failMsg(Map<String, String> map) {
        if (StringUtils.isNotBlank(map.get("mobile")) && StringUtils.isNotBlank(map.get("email"))) {
            map.put("sendType", "1");
            map.put("subjectTitle", "统一用户注册系统信息发送");
            map.put("phoneTo", map.get("mobile"));
            map.put("phoneContent", "您好，您在教育部留学服务中心统一用户身份认证系统中提交的人工找回密码申请未审核通过，原因为:" + map.get("reasonRejection") + "【教留服】");
            map.put("mailTo", map.get("email"));
            map.put("emailContent", "您好，您在教育部留学服务中心统一用户身份认证系统中提交的人工找回密码申请未审核通过，原因为:" + map.get("reasonRejection") + ",如需继续找回密码，可以再次提交人工找回密码申请。教育部留学服务中心");
        }
        //发送短信参数
        return remoteMsgSendService.SendMsg(map);
    }
}