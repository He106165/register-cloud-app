package com.censoft.wechatlogin;

import com.censoft.system.annotation.EnableCenFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCenFeignClients
public class WechartloginApplication {

    public static void main(String[] args) {
        SpringApplication.run(WechartloginApplication.class, args);
    }

}
