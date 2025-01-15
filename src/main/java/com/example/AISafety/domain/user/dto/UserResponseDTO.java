package com.example.AISafety.domain.user.dto;

import com.example.AISafety.domain.user.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private String name;
//    private String email;
//    private String passWord;
//    private String number;
    private RoleType role;
    private String organizationName;
}
