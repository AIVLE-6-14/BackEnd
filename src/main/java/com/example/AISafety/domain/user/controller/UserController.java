package com.example.AISafety.domain.user.controller;

import com.example.AISafety.domain.user.User;
import com.example.AISafety.domain.user.dto.UserEmailDupDTO;
import com.example.AISafety.domain.user.dto.UserLoginDTO;
import com.example.AISafety.domain.user.dto.UserResponseDTO;
import com.example.AISafety.domain.user.dto.UserSignupDTO;
import com.example.AISafety.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @PostMapping("/signup")
    @Operation(summary="회원가입 기능", description = "회원가입을 처리, 회원가입 전에는 Oranization 데이터가 존재해야합니다.")
    public ResponseEntity<Map<String, String>> signup(@RequestBody UserSignupDTO userSignupDTO){
        userService.userRegister(userSignupDTO);
        Map<String, String> response = new HashMap<>();
        response.put("SUCCESS", "회원가입이 성공적으로 완료!");
        response.put("message", "회원가입이 정상적으로 완료되었습니다.");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //세션 기반 로그인 구현, 추후 JWT로 변경 예정
    @PostMapping("/login")
    @Operation(summary="로그인 기능", description = "세션 기반 로그인 처리, 추후 JWT 변경 예정")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserLoginDTO loginDTO, HttpSession session){

        Boolean auth = userService.authenticateUser(loginDTO);
        User user = userService.getUserByEmail(loginDTO);
        Map<String, Object> response = new HashMap<>();

        if(auth){
            // 세션에 사용자 정보 저장
            session.setAttribute("userId", user.getId());

            // UserResponseDTO 객체 생성
            UserResponseDTO userResponseDTO = userService.userResponseDTO(user);

            // 로그인 성공 시 성공 메시지와 사용자 정보 반환
            response.put("SUCCESS", "login 성공");
            response.put("message", userResponseDTO);  // "user" 키로 사용자 정보 반환

            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            // 로그인 실패 시 에러 메시지 반환
            response.put("FAIL", "error");
            response.put("message", "이메일 또는 비밀번호가 일치하지 않습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }


    //로그아웃
    @PostMapping("/logout")
    @Operation(summary="로그아웃 기능", description = "로그아웃을 처리.")
    ResponseEntity<Map<String,String>> logout(HttpSession session) {
        session.invalidate();
        Map<String, String> response = new HashMap<>();
        response.put("SUCCESS", "logout 성공!");
        response.put("message", "logout 에 성공하셨습니다.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/duplicate")
    ResponseEntity<Map<String,String>> duplicatedEmail(@RequestBody UserEmailDupDTO dto){
        Map<String, String> response = new HashMap<>();

        if(userService.isDuplicatedEmail(dto)){
            response.put("FAIL", "중복된 이메일 존재");
            response.put("message", "중복된 이메일이 존재합니다.");
        }else{
            response.put("SUCCESS", "중복된 이메일 존재하지 않음");
            response.put("message", "사용가능한 이매일입니다.");
        }
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
