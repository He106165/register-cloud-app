package com.censoft.system.feign;

import com.censoft.common.constant.ServiceNameConstants;
import com.censoft.common.core.domain.R;
import com.censoft.system.domain.SysUser;
import com.censoft.system.feign.factory.RemoteUserFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 用户 Feign服务层
 *
 * @author censoft
 * @date 2019-05-20
 */
@FeignClient(name = ServiceNameConstants.SYSTEM_SERVICE, fallbackFactory = RemoteUserFallbackFactory.class)
public interface RemoteUserService
{
    @GetMapping("user/find/{username}")
    public SysUser selectSysUserByUsername(@RequestParam("username") String username);

    @PostMapping("user/update/login")
    public R updateUserLoginRecord(@RequestBody SysUser user);

    @PostMapping("user/getInsideUserById")
    public Map getInsideUserById(@RequestParam("userId") Long userId);
}
