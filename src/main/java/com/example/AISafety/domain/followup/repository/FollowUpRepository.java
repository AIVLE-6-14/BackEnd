package com.example.AISafety.domain.followup.repository;

import com.example.AISafety.domain.followup.FollowUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {

}
