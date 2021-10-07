package com.censoft.common.log.event;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import org.springframework.context.ApplicationEvent;


/**
 * 系统日志事件
 */
public class SysOperLogEvent extends ApplicationEvent
{
    //
    private static final long serialVersionUID = 8905017895058642111L;

    public SysOperLogEvent(ManagerLog source)
    {
        super(source);
    }
}