package com.censoft.common.log;

import com.censoft.cendevbackmanage.feign.JouralQueryServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import com.censoft.common.log.aspect.OperLogAspect;
import com.censoft.common.log.listen.LogListener;

import lombok.AllArgsConstructor;

@EnableAsync
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogAutoConfiguration
{
    private final JouralQueryServer jouralQueryServer;

    @Bean
    public LogListener sysOperLogListener()
    {
        return new LogListener(jouralQueryServer);
    }

    @Bean
    public OperLogAspect operLogAspect()
    {
        return new OperLogAspect();
    }
}