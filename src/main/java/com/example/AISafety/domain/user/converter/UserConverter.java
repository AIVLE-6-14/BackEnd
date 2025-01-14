package com.example.AISafety.domain.user.converter;

import com.example.AISafety.domain.user.User;
import com.example.AISafety.domain.user.dto.UserSignupDTO;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {
    public User toEntity(UserSignupDTO userSignupDTO){
        //추후 builder 패턴으로 변환 ?
        User user = new User();
        user.setName(userSignupDTO.getName());
        user.setEmail(userSignupDTO.getEmail());
        user.setPassWord(userSignupDTO.getPassWord());
        user.setRole(userSignupDTO.getRole());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

}
