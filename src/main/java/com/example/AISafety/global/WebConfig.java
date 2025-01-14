package com.example.AISafety.global;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("")// 리액트 개발 환경 주소
                .allowedMethods("GET","POST","DELETE","PUT")
                .allowedHeaders("*");
    }
}
