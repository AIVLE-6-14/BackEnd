//package com.example.AISafety.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable() // H2 Console 사용 시 CSRF 비활성화 필요
//                .headers().frameOptions().disable() // H2 Console 사용 시 Frame Options 비활성화
//                .and()
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/h2-console/**").permitAll() // H2 Console에 대한 접근 허용
//                        .anyRequest().authenticated() // 다른 요청은 인증 필요
//                )
//                .formLogin(); // 기본 로그인 페이지 활성화
//        return http.build();
//    }
//}
