package com.example.AISafety.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://localhost:8000")  // 리액트 프론트엔드와 파이썬  로컬 서버 주소 모두 허용
                .allowedOrigins("http://43.203.198.179:3000") // 리액트 배포 주소
                .allowedOrigins("http://3.37.210.75:3000") // 리액트 재배포 주소
                .allowedOrigins("http://3.37.210.75/ai") // 예측 모델 배포 주소
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .allowedHeaders("*")
                .allowCredentials(true);  // 쿠키와 인증 정보를 포함하여 요청을 보낼 수 있도록 허용
    }
}
