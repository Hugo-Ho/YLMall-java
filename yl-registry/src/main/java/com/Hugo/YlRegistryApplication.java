package com.Hugo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class YlRegistryApplication {
    public void main(String[] args)
    {
        SpringApplication.run(YlRegistryApplication.class,args);
    }
}
