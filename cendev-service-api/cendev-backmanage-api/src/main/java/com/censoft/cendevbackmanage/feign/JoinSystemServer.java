package com.censoft.cendevbackmanage.feign;

import com.censoft.cendevbackmanage.entity.JoinSystemInfo;
import com.censoft.cendevbackmanage.feign.factory.RemoteJoinSystemFallbackFactory;
import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.common.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANAGE,fallbackFactory = RemoteJoinSystemFallbackFactory.class)
public interface JoinSystemServer {

    //查询应用系统数据项
    @GetMapping(value = "joinSystemInfo/getSystemDataConfig")
    public JSONObject getSystemDataConfig(@RequestParam("code")String code);
    //检测系统对接是否存在
    @GetMapping("joinSystemInfo/checkSystemCode")
    public Boolean checkSystemCode(@RequestParam("code")String code);
    // 查询应用系统的 返回地址
    @GetMapping("joinSystemInfo/getSystemRetUrl")
    public String getSystemRetUrl(@RequestParam("joinsysCode") String joinsysCode);
}
