package com.censoft;

import com.censoft.system.annotation.EnableCenFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCenFeignClients
public class UserregisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserregisterApplication.class, args);
    }

}
