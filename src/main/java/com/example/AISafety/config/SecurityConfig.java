package com.example.AISafety.config;

import com.example.AISafety.global.security.jwt.JwtAuthenticationFilter;
import com.example.AISafety.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true,prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .cors()
                .and()
                .headers().frameOptions().disable()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 비활성화
                .and()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/",
                                "/api/users/login", // 로그인
                                "/api/users/signup", //회원가입
                                "/api/users/duplicate", // 이메일 중복 체크
                                "/api/users/logout", //로그아웃
                                "/api/animals/fetch", // 동물감지 등록
                                "/api/auth/register",
                                "/api/auth/verify",
                                "/api/organizations/**" // 기관 등록
                        ).permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers(
                                "swagger-ui/**", //swagger UI 접근 허용
                                "/v3/api-docs/**", //swagger OpenAPI 문서
                                "/swagger-resources/**", // swagger 리소스
                                "/webjars/**" // swagger webjar
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
