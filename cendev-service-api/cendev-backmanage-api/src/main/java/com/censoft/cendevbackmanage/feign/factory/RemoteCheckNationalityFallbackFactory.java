package com.censoft.cendevbackmanage.feign.factory;

import com.censoft.cendevbackmanage.entity.ManagerLog;
import com.censoft.cendevbackmanage.feign.CheckNationalityServer;
import com.censoft.cendevbackmanage.feign.JouralQueryServer;
import com.censoft.common.core.domain.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RemoteCheckNationalityFallbackFactory implements FallbackFactory<CheckNationalityServer> {

    @Override
    public CheckNationalityServer create(Throwable throwable) {
        return new CheckNationalityServer() {

            @Override
            public List<Map> selectNationalityListByCode(Map map) {
                return null;
            }

            @Override
            public Map selectNationalityAllCode(Map map) {
                System.out.println("触发熔断，请检查backmanage服务");
                return null;
            }
        };
    }
}
