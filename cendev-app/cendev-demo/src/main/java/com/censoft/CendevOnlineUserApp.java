package com.censoft;

import com.censoft.system.annotation.EnableCenFeignClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 启动程序
 *
 * @author censoft
 */
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
//@EnableEurekaClient
//@EnableCenFeignClients

@SpringBootApplication
@EnableEurekaClient
@EnableCenFeignClients
public class CendevOnlineUserApp
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(CendevOnlineUserApp.class, args);
    }
}
