package com.example.AISafety.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.create("http://localhost:3000"); // 기본 URL 설정 (필요한 경우 수정)
    }
}
