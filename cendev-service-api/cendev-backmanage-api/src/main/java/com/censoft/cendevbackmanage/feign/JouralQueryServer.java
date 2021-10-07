package com.censoft.cendevbackmanage.feign;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import com.censoft.cendevbackmanage.feign.factory.RemoteJouralQueryFallbackFactory;
import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANAGE, fallbackFactory = RemoteJouralQueryFallbackFactory.class)
@Component
public interface JouralQueryServer {

    //插入日志
    @RequestMapping(value = "managerLog/save",method = RequestMethod.POST)
    public R addSave(@RequestBody ManagerLog managerLog);
}
