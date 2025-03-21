package com.example.AISafety.domain.user.repository;

import com.example.AISafety.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
    User findByEmail(String email);

    Boolean existsByEmail(String email);

}
