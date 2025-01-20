package com.example.AISafety.domain.user.controller;

import com.example.AISafety.config.JwtTokenProvider;
import com.example.AISafety.domain.user.dto.UserEmailDupDTO;
import com.example.AISafety.domain.user.dto.UserLoginDTO;
import com.example.AISafety.domain.user.dto.UserSignupDTO;
import com.example.AISafety.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name="User API", description = "회원 관련 API 제공합니다.")
public class UserController {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원가입 기능 (세션 방식 유지)
    @PostMapping("/signup")
    @Operation(summary="회원가입 기능", description = "회원가입을 처리, 회원가입 전에는 Organization 데이터가 존재해야합니다.")
    public ResponseEntity<Map<String, String>> signup(@RequestBody UserSignupDTO userSignupDTO){
        userService.userRegister(userSignupDTO);
        Map<String, String> response = new HashMap<>();
        response.put("SUCCESS", "회원가입이 성공적으로 완료!");
        response.put("message", "회원가입이 정상적으로 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 로그인 기능 (JWT 방식)
    @PostMapping("/login")
    @Operation(summary = "로그인 기능", description = "토큰 로그인 처리, JWT")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginDTO loginDTO) {
        boolean isValidUser = userService.authenticateUser(loginDTO);  // 사용자 인증
        Map<String, String> response = new HashMap<>();
        if (isValidUser) {
            // 사용자 정보 가져오기
            var user = userService.getUserByEmail(loginDTO);

            // JWT 토큰 생성
            String token = jwtTokenProvider.createToken(
                    String.valueOf(user.getId()),  // 사용자 ID
                    user.getEmail(),                // 사용자 이메일
                    user.getRole().name(),          // 사용자 역할
                    user.getOrganization().getId(), // 사용자 조직 ID
                    user.getName()                  // 사용자 이름
            );

            // 응답으로 토큰과 메시지 전송
            response.put("token", token);
            response.put("message", "로그인 성공");
            return ResponseEntity.ok(response);
        } else {
            // 인증 실패 시 메시지
            response.put("message", "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    // 로그아웃 기능 (클라이언트에서 JWT 삭제)
    @PostMapping("/logout")
    @Operation(summary = "로그아웃 기능", description = "JWT 토큰을 삭제하여 로그아웃을 처리합니다.")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        Map<String, String> response = new HashMap<>();
        // 클라이언트에서 JWT를 삭제하는 방식으로 로그아웃 처리
        response.put("SUCCESS", "로그아웃 성공");
        response.put("message", "JWT 토큰을 삭제하여 로그아웃 되었습니다.");

        return ResponseEntity.ok(response);
    }

    // 이메일 중복 확인 기능 (세션 방식 유지)
    @PostMapping("/duplicate")
    @Operation(summary="이메일 중복 확인", description = "해당 이메일이 중복 인지 아닌지 알려줍니다.")
    ResponseEntity<Map<String,String>> duplicatedEmail(@RequestBody UserEmailDupDTO dto){
        Map<String, String> response = new HashMap<>();

        if(userService.isDuplicatedEmail(dto)){
            response.put("FAIL", "중복된 이메일 존재");
            response.put("message", "중복된 이메일이 존재합니다.");
        }else{
            response.put("SUCCESS", "중복된 이메일 존재하지 않음");
            response.put("message", "사용가능한 이메일입니다.");
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /*
    // 기존의 세션 기반 로그인 구현 (주석 처리)
    @PostMapping("/login")
    @Operation(summary="로그인 기능", description = "세션 기반 로그인 처리")
    public ResponseEntity<Map<String, String>> login(HttpSession session, @RequestBody UserLoginDTO loginDTO) {
        boolean isValidUser = userService.authenticateUser(loginDTO);
        Map<String, String> response = new HashMap<>();
        if (isValidUser) {
            session.setAttribute("userId", loginDTO.getEmail()); // 세션에 사용자 이메일 저장
            response.put("message", "로그인 성공");
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "로그인 실패: 이메일 또는 비밀번호가 잘못되었습니다.");
            return ResponseEntity.status(401).body(response);
        }
    }

    // 기존의 세션 기반 로그아웃 처리 (주석 처리)
    @PostMapping("/logout")
    @Operation(summary="로그아웃 기능", description = "세션 기반 로그아웃 처리")
    ResponseEntity<Map<String,String>> logout(HttpSession session) {
        session.invalidate(); // 세션 무효화
        Map<String, String> response = new HashMap<>();
        response.put("SUCCESS", "logout 성공!");
        response.put("message", "logout 에 성공하셨습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    */
}
