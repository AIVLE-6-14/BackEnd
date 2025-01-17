package com.example.AISafety.domain.dashboard.repository;

import com.example.AISafety.domain.dashboard.dto.AnimalDashboardDTO;
import com.example.AISafety.domain.animal.Animal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DashboardRepository extends CrudRepository<Animal, Long> {

    @Query("SELECT new com.example.AISafety.domain.dashboard.dto.AnimalDashboardDTO(a.name, COUNT(a.id)) " +
            "FROM Animal a " +
            "WHERE a.detectedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY a.name")
    List<AnimalDashboardDTO> findAnimalDistribution(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
