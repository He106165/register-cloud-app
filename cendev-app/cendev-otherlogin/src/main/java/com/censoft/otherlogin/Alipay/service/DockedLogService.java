package com.censoft.otherlogin.Alipay.service;

import com.censoft.cendevbackmanage.feign.DockedLogServer;
import com.censoft.common.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANGEEAll)
//@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANGEE, fallbackFactory = RemoteTokenFallbackFactory.class)
@Service
public interface DockedLogService extends DockedLogServer {
}
