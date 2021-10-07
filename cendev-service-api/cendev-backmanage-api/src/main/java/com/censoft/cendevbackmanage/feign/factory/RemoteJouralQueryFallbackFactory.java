package com.censoft.cendevbackmanage.feign.factory;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import com.censoft.cendevbackmanage.feign.JouralQueryServer;
import com.censoft.common.core.domain.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RemoteJouralQueryFallbackFactory  implements FallbackFactory<JouralQueryServer> {

    @Override
    public JouralQueryServer create(Throwable throwable) {
        return new JouralQueryServer(){
            @Override
            public R addSave(ManagerLog managerLog) {
                return null;
            }
        };
    }
}
