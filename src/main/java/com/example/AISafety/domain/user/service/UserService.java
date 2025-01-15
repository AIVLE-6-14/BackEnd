package com.example.AISafety.domain.user.service;

import com.example.AISafety.domain.organization.Organization;
import com.example.AISafety.domain.organization.repository.OrganizationRepository;
import com.example.AISafety.domain.user.User;
import com.example.AISafety.domain.user.dto.UserLoginDTO;
import com.example.AISafety.domain.user.dto.UserSignupDTO;
import com.example.AISafety.domain.user.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    public void userRegister(UserSignupDTO userSignupDTO){
        if(userRepository.existsByEmail(userSignupDTO.getEmail())){ throw new IllegalArgumentException("해당 이메일은 이미 존재합니다.");}
        if(!organizationRepository.existsByName(userSignupDTO.getOrganizationName())){
            throw new IllegalArgumentException("해당 기관은 존재하지 않는 기관입니다.");
        }else{
            Organization organization = organizationRepository.findByName(userSignupDTO.getOrganizationName());

            User user = new User();
            user.setName(userSignupDTO.getName());
            user.setPhoneNumber(userSignupDTO.getNumber());
            user.setEmail(userSignupDTO.getEmail());
            user.setPassWord(userSignupDTO.getPassWord());
            user.setRole(userSignupDTO.getRole());
            user.setOrganization(organization);
            user.setCreatedAt(LocalDateTime.now());

            userRepository.save(user);
        }

    }

    public boolean authenticateUser(UserLoginDTO loginDTO){
        User user = userRepository.findByEmail(loginDTO.getEmail());

        if(user != null && user.getPassWord().equals(loginDTO.getPassWord())){
            return true;
        }
        return false;
    }

    public User getUserByEmail(UserLoginDTO loginDTO){
        return userRepository.findByEmail(loginDTO.getEmail());
    }

    public User getUserById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 user를 찾을 수 없습니다."));
    }
}
