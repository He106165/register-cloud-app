package com.censoft.cendevbackmanage.feign;

import com.censoft.cendevbackmanage.feign.factory.RemoteCheckNationalityFallbackFactory;
import com.censoft.common.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANAGE, fallbackFactory = RemoteCheckNationalityFallbackFactory.class)
@Component
public interface CheckNationalityServer {

    //插入日志
    @RequestMapping(value = "nationality/list",method = RequestMethod.POST)
    public List<Map> selectNationalityListByCode(@RequestParam Map map);
    @RequestMapping(value = "nationality/allThreeCode",method = RequestMethod.POST)
    public Map selectNationalityAllCode(@RequestParam Map map);
}
