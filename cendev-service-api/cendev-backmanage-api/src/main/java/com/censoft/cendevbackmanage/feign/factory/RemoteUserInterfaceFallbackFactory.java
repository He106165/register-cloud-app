package com.censoft.cendevbackmanage.feign.factory;

import com.censoft.cendevbackmanage.feign.UserInterfaceServer;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteUserInterfaceFallbackFactory implements FallbackFactory<UserInterfaceServer> {

    @Override
    public UserInterfaceServer create(Throwable throwable) {
        return new UserInterfaceServer(){
            @Override
            public boolean certTwoData(String userName, String cardNo) {
                System.out.println("实名认证接口出错，触发熔断 参数：" + userName + "   " + cardNo);
                return false;
            }
        };
    }
}
