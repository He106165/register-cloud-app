package com.censoft.cendevbackmanage.feign.factory;

import com.censoft.cendevbackmanage.entity.JoinSystemInfo;
import com.censoft.cendevbackmanage.entity.JoinSystemRegisterlog;
import com.censoft.cendevbackmanage.feign.DockedLogServer;
import com.censoft.cendevbackmanage.feign.JoinSystemServer;
import com.censoft.common.json.JSONObject;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class RemoteDockedLogFallbackFactory implements FallbackFactory<DockedLogServer> {

    @Override
    public DockedLogServer create(Throwable throwable) {
        return new DockedLogServer(){

            @Override
            public String sysLogaddSave(JoinSystemRegisterlog joinSystemRegisterlog) {
                return null;
            }

            @Override
            public List<JoinSystemInfo> sysInfolist(JoinSystemInfo joinSystemInfo) {
                return null;
            }
        };
    }
}
