package com.censoft.common.log.publish;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import com.censoft.common.constant.Constants;
import com.censoft.common.log.event.SysLogininforEvent;
import com.censoft.common.utils.AddressUtils;
import com.censoft.common.utils.IpUtils;
import com.censoft.common.utils.ServletUtils;
import com.censoft.common.utils.spring.SpringContextHolder;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;

public class PublishFactory
{
    /**
     * 记录登陆信息
     * 
     * @param username 用户名
     * @param status 状态
     * @param message 消息
     * @param args 列表
     */
    public static void recordLogininfor(final String username, final String status, final String message,final String opType,String userId,String userType,
            final Object ... args)
    {
        HttpServletRequest request = ServletUtils.getRequest();
        final String ip = IpUtils.getIpAddr(request);
        // 封装对象
        ManagerLog logininfor = new ManagerLog();
        logininfor.setOpUserId(userId);
        logininfor.setOpUserType(userType);
        logininfor.setOpUserName(username);
        logininfor.setOpIp(ip);
        logininfor.setOpLocation(AddressUtils.getRealAddressByIP(ip));
        logininfor.setMsg(message);
        logininfor.setOpType(opType);
        // 日志状态
        if (Constants.LOGIN_SUCCESS.equals(status) || Constants.LOGOUT.equals(status) || Constants.KICK_LOGOUT.equals(status))
        {
            logininfor.setStatus(Constants.SUCCESS);
        }
        else if (Constants.LOGIN_FAIL.equals(status))
        {
            logininfor.setStatus(Constants.FAIL);
        }
        // 发布事件
        SpringContextHolder.publishEvent(new SysLogininforEvent(logininfor));
    }
}
