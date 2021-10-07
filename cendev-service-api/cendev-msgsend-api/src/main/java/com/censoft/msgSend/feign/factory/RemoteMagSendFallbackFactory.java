package com.censoft.msgSend.feign.factory;

import com.censoft.msgSend.feign.RemoteMsgSendService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class RemoteMagSendFallbackFactory implements FallbackFactory<RemoteMsgSendService>
{

    @Override
    public RemoteMsgSendService create(Throwable throwable) {
        return new RemoteMsgSendService() {
            @Override
            public boolean SendMsg(Map map) {
                return false;
            }
        };
    }
}
