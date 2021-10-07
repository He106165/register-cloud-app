package com.censoft.personallogin.service;

import com.censoft.cendevbackmanage.feign.JoinSystemServer;
import com.censoft.common.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;

@Service
@FeignClient(name = ServiceNameConstants.CENDEV_BACKMANAGE)
public interface ToJoinSystemService extends JoinSystemServer {
}
