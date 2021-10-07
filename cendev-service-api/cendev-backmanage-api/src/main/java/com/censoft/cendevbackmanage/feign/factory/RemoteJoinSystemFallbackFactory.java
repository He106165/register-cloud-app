package com.censoft.cendevbackmanage.feign.factory;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import com.censoft.cendevbackmanage.feign.JoinSystemServer;
import com.censoft.cendevbackmanage.feign.JouralQueryServer;
import com.censoft.common.core.domain.R;
import com.censoft.common.json.JSONObject;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteJoinSystemFallbackFactory implements FallbackFactory<JoinSystemServer> {

    @Override
    public JoinSystemServer create(Throwable throwable) {
        return new JoinSystemServer(){

            @Override
            public JSONObject getSystemDataConfig(String code) {
                return null;
            }

            @Override
            public Boolean checkSystemCode(String code) {
                return false;
            }

            @Override
            public String getSystemRetUrl(String joinsysCode) {
                return null;
            }
        };
    }
}
