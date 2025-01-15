package com.example.AISafety.domain.organization.repository;

import com.example.AISafety.domain.organization.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {
    Organization findByName(String name);
    Boolean existsByName(String Name);


}
