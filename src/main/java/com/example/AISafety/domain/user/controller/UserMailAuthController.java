package com.example.AISafety.domain.user.controller;

import com.example.AISafety.domain.user.dto.UserSignupDTO;
import com.example.AISafety.domain.user.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserMailAuthController {

    private final EmailService emailService;
    private final Map<String, String> verificationCodes = new HashMap<>();

    @Autowired
    public UserMailAuthController(EmailService emailService) {
        this.emailService = emailService;
    }

    /**
     * 회원가입 요청 처리 - 6자리 인증 코드 발송
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody UserSignupDTO userSignupDTO) {
        Map<String, Object> response = new HashMap<>();
        boolean emailSent = false;

        try {
            // 랜덤 6자리 코드 생성
            String verificationCode = generateVerificationCode();

            // 인증 코드 저장 (실제 DB 사용 권장)
            verificationCodes.put(userSignupDTO.getEmail(), verificationCode);

            // 메일 발송
            emailService.sendEmail(
                    userSignupDTO.getEmail(),
                    "회원가입 인증 코드",
                    //"인증 코드: " + verificationCode + "를 입력하여 회원가입을 완료하세요."
                    verificationCode
            );

            emailSent = true;
            response.put("SUCCESS", "인증 코드가 이메일로 발송되었습니다.");
        } catch (Exception e) {
            response.put("FAIL", "이메일 발송에 실패했습니다. 관리자에게 문의하세요.");
        }

        response.put("message", emailSent);
        return ResponseEntity.ok(response);
    }


    /**
     * 이메일 인증 코드 검증
     */
//    @PostMapping("/verify")
//    public ResponseEntity<Map<String, Object>> verifyUser(@RequestParam String email, @RequestParam String code) {
//        Map<String, Object> response = new HashMap<>();
//        boolean verificationResult = false;
//
//        if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(code)) {
//            verificationCodes.remove(email);  // 인증 완료 후 코드 제거
//            verificationResult = true;
//            response.put("SUCCESS", "메일 인증 성공");
//        } else {
//            response.put("FAIL", "인증 코드가 유효하지 않거나 이메일이 잘못되었습니다.");
//        }
//
//        response.put("message", verificationResult); // Boolean 값 추가
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
    /**
     * 이메일 인증 코드 검증
     */
    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String code = body.get("code");

        Map<String, Object> response = new HashMap<>();
        boolean verificationResult = false;

        if (verificationCodes.containsKey(email) && verificationCodes.get(email).equals(code)) {
            verificationCodes.remove(email);  // 인증 완료 후 코드 제거
            verificationResult = true;
            response.put("SUCCESS", "메일 인증 성공");
        } else {
            response.put("FAIL", "인증 코드가 유효하지 않거나 이메일이 잘못되었습니다.");
        }

        response.put("message", verificationResult); // Boolean 값 추가
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 랜덤 6자리 숫자/문자 코드 생성 메소드
     */
    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        for (int i = 0; i < 6; i++) {
            code.append(charset.charAt(random.nextInt(charset.length())));
        }
        return code.toString();
    }
}
