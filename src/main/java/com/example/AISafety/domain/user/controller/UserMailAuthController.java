package com.example.AISafety.domain.user.controller;

import com.example.AISafety.domain.user.dto.UserSignupDTO;
import com.example.AISafety.domain.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class UserMailAuthController {

    private final EmailService emailService;

    @Autowired
    public UserMailAuthController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * 회원가입 요청 처리
     * 메일 인증을 위해 이메일로 링크 발송
     * @param userRequest 회원 요청 정보 (이메일)
     * @return 인증 메일 발송 메시지
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserSignupDTO.UserRequest userRequest) {
        // 고유 토큰 생성
        String token = UUID.randomUUID().toString();

        // 인증 링크 생성
        String verificationLink = "http://localhost:8080/api/auth/verify?token=" + token;

        // 메일 발송
        emailService.sendEmail(userRequest.getEmail(), "회원가입 인증",
                "다음 링크를 클릭하여 인증을 완료하세요: " + verificationLink);

        return ResponseEntity.ok("인증 메일이 발송되었습니다. 메일에서 링크를 확인하세요.");
    }

    /**
     * 이메일 인증 확인 메소드
     * @param token 이메일 인증 토큰
     * @return 인증 성공 메시지
     */
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String token) {
        // TODO: 실제 토큰 검증 로직 추가
        return ResponseEntity.ok("인증 성공! 이제 로그인 가능합니다.");
    }
}
