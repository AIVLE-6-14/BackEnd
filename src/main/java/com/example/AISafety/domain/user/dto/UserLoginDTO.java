package com.example.AISafety.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginDTO {
    //추후 스프링 시큐리티 도입 시 토큰 반환으로 변경
    private String email;
    private String passWord;
}
