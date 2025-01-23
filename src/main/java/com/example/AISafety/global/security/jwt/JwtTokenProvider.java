package com.example.AISafety.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // 비밀키 생성 (실제 운영에서는 안전하게 관리 필요)
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 토큰 유효 기간 설정 (1시간)
    private final long validityInMilliseconds = 3600000;

    // 토큰 생성
    public String createToken(String userId, String email, String role, Long organizationId, String name) {
        Claims claims = Jwts.claims().setSubject(email); // 토큰 주제
        claims.put("role", role); // 추가 정보 저장
        claims.put("organization_id", organizationId); // 조직 ID 추가
        claims.put("user_id", userId); // 사용자 ID 추가
        claims.put("name", name); // 사용자 이름 추가

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now) // 생성 시간
                .setExpiration(validity) // 만료 시간
                .signWith(secretKey) // 서명
                .compact();
    }

    // 토큰에서 이메일 추출
    public String getEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // 토큰에서 사용자 ID 추출
    public String getUserId(String token) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("user_id");
    }

    // 토큰에서 조직 ID 추출
    public Long getOrganizationId(String token) {
        return Long.valueOf((String) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("organization_id"));
    }

    // 토큰에서 사용자 이름 추출
    public String getName(String token) {
        return (String) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("name");
    }

    //토큰에서 역할 추출
    public String getRole(String token){
        return (String) Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("role");
    }
}
