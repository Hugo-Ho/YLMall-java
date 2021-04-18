package com.Hugo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class YlGoodsServiceApplication {
    public static void main(String[] args)
    {
        SpringApplication.run(YlGoodsServiceApplication.class,args);
    }
}
