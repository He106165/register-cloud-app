package com.censoft.cendevbackmanage.feign;

import com.censoft.cendevbackmanage.entity.JoinSystemInfo;
import com.censoft.cendevbackmanage.entity.JoinSystemRegisterlog;
import com.censoft.cendevbackmanage.feign.factory.RemoteDockedLogFallbackFactory;
import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.common.core.domain.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * token Feign服务层
 * 
 * @author censoft
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANGEEAll ,fallbackFactory = RemoteDockedLogFallbackFactory.class)
@Service
public interface DockedLogServer
{

    //添加系统对接日志
    @RequestMapping(value = "Jointlog/sysLogaddSave",method = RequestMethod.POST)
    public String sysLogaddSave( @RequestBody JoinSystemRegisterlog joinSystemRegisterlog);



    //检测系统对接是否存在
    @GetMapping (value = "joinSystemInfo/sysInfolist")
    public List<JoinSystemInfo> sysInfolist(@RequestParam("sysInfo") JoinSystemInfo joinSystemInfo);
}
