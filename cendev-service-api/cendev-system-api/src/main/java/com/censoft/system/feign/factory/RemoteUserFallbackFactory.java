package com.censoft.system.feign.factory;

import org.springframework.stereotype.Component;

import com.censoft.common.core.domain.R;
import com.censoft.system.domain.SysUser;
import com.censoft.system.feign.RemoteUserService;

import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Component
public class RemoteUserFallbackFactory implements FallbackFactory<RemoteUserService>
{
    @Override
    public RemoteUserService create(Throwable throwable)
    {
        log.error(throwable.getMessage());
        return new RemoteUserService()
        {
            @Override
            public SysUser selectSysUserByUsername(String username)
            {
                return null;
            }

            @Override
            public R updateUserLoginRecord(SysUser user)
            {
                return R.error();
            }

            @Override
            public Map getInsideUserById(Long userId) {
                return null;
            }
        };
    }
}
