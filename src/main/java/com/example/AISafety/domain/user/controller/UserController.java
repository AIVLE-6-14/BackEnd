package com.example.AISafety.domain.user.controller;

import com.example.AISafety.domain.user.dto.UserSignupDTO;
import com.example.AISafety.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        response.put("message", "회원가입이 성공적으로 완료!");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
