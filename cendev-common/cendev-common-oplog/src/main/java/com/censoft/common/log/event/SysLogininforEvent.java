package com.censoft.common.log.event;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import org.springframework.context.ApplicationEvent;

/**
 * 系统日志事件
 */
public class SysLogininforEvent extends ApplicationEvent
{
    //
    private static final long serialVersionUID = -9084676463718966036L;

    public SysLogininforEvent(ManagerLog source)
    {
        super(source);
    }
}