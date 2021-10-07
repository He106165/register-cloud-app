package com.censoft;

import com.censoft.dfs.annotation.EnableCenFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
@EnableCenFeignClients
public class PersonalcenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalcenterApplication.class, args);
    }

}
