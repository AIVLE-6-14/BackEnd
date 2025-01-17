package com.example.AISafety.domain.dashboard.controller;

import com.example.AISafety.domain.dashboard.dto.AnimalDashboardDTO;
import com.example.AISafety.domain.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/animal-distribution")
    public List<AnimalDashboardDTO> getAnimalDistribution() {
        return dashboardService.getAnimalDistribution();
    }
}
