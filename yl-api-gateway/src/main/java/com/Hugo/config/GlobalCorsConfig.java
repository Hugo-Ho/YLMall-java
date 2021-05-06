package com.Hugo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class GlobalCorsConfig {
    @Bean
    public CorsFilter corsFilter()
    {
        //1.添加CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //1)允许的域 如果填写了*将不能操作cookies
        config.addAllowedOrigin("*");
        //2)是否发送cookies
        //config.setAllowCredentials(true);
        //3)允许的请求方式
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        //4)允许的头信息
        config.addAllowedHeader("");

        //2.添加映射路径，统一拦截一起请求
        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**",config);

        //3.返回新的CorsFilter
        return new CorsFilter(configurationSource);
    }
}
