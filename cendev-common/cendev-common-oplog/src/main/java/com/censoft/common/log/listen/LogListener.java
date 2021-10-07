package com.censoft.common.log.listen;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import com.censoft.cendevbackmanage.feign.JouralQueryServer;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import com.censoft.common.log.event.SysLogininforEvent;
import com.censoft.common.log.event.SysOperLogEvent;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class LogListener
{
    private final JouralQueryServer jouralQueryServer;

    @Async
    @Order
    @EventListener(SysOperLogEvent.class)
    public void listenOperLog(SysOperLogEvent event)
    {
        ManagerLog managerLog = (ManagerLog) event.getSource();
        jouralQueryServer.addSave(managerLog);
        log.info("远程操作日志记录成功：{}", managerLog);
    }

    @Async
    @Order
    @EventListener(SysLogininforEvent.class)
    public void listenLoginifor(SysLogininforEvent event)
    {
        ManagerLog managerLog = (ManagerLog) event.getSource();
        jouralQueryServer.addSave(managerLog);
        log.info("远程访问日志记录成功：{}", managerLog);
    }
}