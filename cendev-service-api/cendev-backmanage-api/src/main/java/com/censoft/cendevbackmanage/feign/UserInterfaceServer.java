package com.censoft.cendevbackmanage.feign;

import com.censoft.cendevbackmanage.feign.factory.RemoteUserInterfaceFallbackFactory;
import com.censoft.common.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANAGE, fallbackFactory = RemoteUserInterfaceFallbackFactory.class)
@Component
public interface UserInterfaceServer {
    /**
     * 自然人实名认证接口
     */
    @RequestMapping(value = "userInterface/certTwoData",method = RequestMethod.POST)
    public boolean certTwoData(@RequestParam("userName") String userName, @RequestParam("cardNo") String cardNo);
}
