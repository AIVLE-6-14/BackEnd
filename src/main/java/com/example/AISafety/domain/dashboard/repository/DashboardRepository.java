package com.example.AISafety.domain.dashboard.repository;

import com.example.AISafety.domain.animal.Animal;
import com.example.AISafety.domain.dashboard.dto.BarDTO;
import com.example.AISafety.domain.dashboard.dto.LineDTO;
import com.example.AISafety.domain.dashboard.dto.MapDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface DashboardRepository extends CrudRepository<Animal, Long> {

    @Query("SELECT new com.example.AISafety.domain.dashboard.dto.BarDTO(a.name, COUNT(a.id)) " +
            "FROM Animal a " +
            "WHERE a.detectedAt BETWEEN :startDate AND :endDate " +
            "GROUP BY a.name")
    List<BarDTO> findAnimalDistribution(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT new com.example.AISafety.domain.dashboard.dto.MapDTO(a.name,a.latitude, a.longitude)" +
            "FROM Animal a")
    List<MapDTO> findAnimal();

    @Query("SELECT new com.example.AISafety.domain.dashboard.dto.LineDTO(" +
            "CAST(f.createdAt AS java.time.LocalDate), " +
            "SUM(CASE WHEN f.organization.id = 1 AND f.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN f.organization.id != 1 AND f.status = 'COMPLETED' THEN 1 ELSE 0 END)) " +
            "FROM FollowUp f " +
            "WHERE f.status = 'COMPLETED' " +
            "GROUP BY CAST(f.createdAt AS java.time.LocalDate) " +
            "ORDER BY CAST(f.createdAt AS java.time.LocalDate) ASC")
    List<LineDTO> getFollowUpCountsByDate();


}
