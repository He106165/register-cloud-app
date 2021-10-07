package com.censoft.msgSend.feign;

import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.msgSend.feign.factory.RemoteMagSendFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 消息发送Feign服务层
 * 使用规则
 * mailTo	        邮件接收方     	 发送的类型为1、2时必填
 * phoneTo	        短信接收方        发送的类型为1、3时必填
 * subjectTitle	    邮件发送的主题		(必填)，邮件即是发送的标题
 * emailContent	    邮件内容	        发送的类型为1、2时必填
 * phoneContent	    短信内容		    发送的类型为1、3时必填
 * sendType	        发送的类型		1.发送邮件短信；2.仅邮件；3.仅短信
 * type	            类型	String(2)	1.csce（默认）；2.合作办学；后面可添加
 *
 * @author censoft
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.CENDEV_MAGSEND, fallbackFactory = RemoteMagSendFallbackFactory.class)
@Service
public interface RemoteMsgSendService
{
    @PostMapping("MsgSendInfo/SendMsg")
    public boolean SendMsg(@RequestParam Map map);
}
