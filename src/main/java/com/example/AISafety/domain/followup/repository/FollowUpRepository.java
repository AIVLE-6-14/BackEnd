package com.example.AISafety.domain.followup.repository;

import com.example.AISafety.domain.followup.FollowUp;
import com.example.AISafety.domain.followup.Status;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowUpRepository extends JpaRepository<FollowUp, Long> {
    List<FollowUp> findByOrganizationIdAndStatus(Long organizationId, Status status);

    List<FollowUp> findByStatus(Status status);
}
