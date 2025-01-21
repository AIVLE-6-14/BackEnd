package com.example.AISafety.domain.user.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.AISafety.domain.organization.Organization;
import com.example.AISafety.domain.organization.repository.OrganizationRepository;
import com.example.AISafety.domain.user.RoleType;
import com.example.AISafety.domain.user.User;
import com.example.AISafety.domain.user.dto.UserEmailDupDTO;
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
    private final PasswordEncoder passwordEncoder;

    public void userRegister(UserSignupDTO userSignupDTO) {
        if (userRepository.existsByEmail(userSignupDTO.getEmail())) {
            throw new IllegalArgumentException("해당 이메일은 이미 존재합니다.");
        }
        if (!organizationRepository.existsByName(userSignupDTO.getOrganizationName())) {
            throw new IllegalArgumentException("해당 기관은 존재하지 않는 기관입니다.");
        } else {
            Organization organization = organizationRepository.findByName(
                    userSignupDTO.getOrganizationName());

            RoleType role = RoleType.SAFETY_USER;
            if (organization.getName().equals("도로교통 공사")) {
                role = RoleType.ROAD_USER;
            }
            String encodedPassword = passwordEncoder.encode(userSignupDTO.getPassWord());

            User user = new User();
            user.setName(userSignupDTO.getName());
            user.setPhoneNumber(userSignupDTO.getNumber());
            user.setEmail(userSignupDTO.getEmail());
            user.setPassWord(encodedPassword);
            user.setRole(role);
            user.setOrganization(organization);
            user.setCreatedAt(LocalDateTime.now());

            userRepository.save(user);
        }

    }


    public User getUserByEmail(UserLoginDTO loginDTO) {
        return userRepository.findByEmail(loginDTO.getEmail());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 user를 찾을 수 없습니다."));
    }

    public Boolean isDuplicatedEmail(UserEmailDupDTO dto) {
        return userRepository.existsByEmail(dto.getEmail());
    }

    public boolean checkUser(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByEmail(userLoginDTO.getEmail());
        return user != null && passwordEncoder.matches(userLoginDTO.getPassWord(),
                user.getPassWord());

    }



}
