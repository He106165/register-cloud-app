package com.censoft.otherlogin.feign;

import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.otherlogin.feign.factory.RemoteTokenFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * token Feign服务层
 *
 * @author censoft
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.CENDEV_OThERLOGIN, fallbackFactory = RemoteTokenFallbackFactory.class)
@Service
public interface RemoteTokenServer
{
    @GetMapping("token/getToken")
    public String  getToken(@RequestParam("accoutName") String accoutName);

    @GetMapping("token/getAccoutName")
    public String getAccoutName(@RequestParam("token")  String token);

    @GetMapping("token/checkToken")
    public boolean  checkToken(@RequestParam("token")  String token);

    @GetMapping("token/getTokenConfigTime")
    public String getTokenConfigTime();
}
