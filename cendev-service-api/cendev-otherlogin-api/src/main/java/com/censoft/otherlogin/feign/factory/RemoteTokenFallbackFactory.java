package com.censoft.otherlogin.feign.factory;

import com.censoft.otherlogin.feign.RemoteTokenServer;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteTokenFallbackFactory implements FallbackFactory<RemoteTokenServer>
{
    private static final Logger logger = LoggerFactory.getLogger(RemoteTokenFallbackFactory.class);


    @Override
    public RemoteTokenServer create(Throwable throwable) {
        return  new RemoteTokenServer() {
            @Override
            public String getToken(String accoutName) {
                logger.error("创建token出错了,请检查otherLogin服务");
                return null;
            }
            @Override
            public String getAccoutName(String token) {
                logger.error("获取token信息出错了,请检查otherLogin服务");
                return null;
            }
            @Override
            public boolean checkToken(String token) {
                logger.error("检验token出错了,请检查otherLogin服务");
                return false;
            }

            @Override
            public String getTokenConfigTime() {
                logger.error("获取token配置出错了,请检查otherLogin服务");
                return null;
            }
        };
    }
}
