package com.censoft;

import com.censoft.otherlogin.annotation.EnableCenFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCenFeignClients
public class OtherloginApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtherloginApplication.class, args);
    }

}
