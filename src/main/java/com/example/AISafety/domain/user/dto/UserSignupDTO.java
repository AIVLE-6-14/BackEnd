package com.example.AISafety.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDTO {
    private String name;
    private String email;
    private String passWord;
    private String number;
    private String organizationName;

    /**
     * 회원가입 요청 정보를 담는 DTO 클래스
     */
    @Setter
    @Getter
    public static class UserRequest {

        private String email;

    }
}
