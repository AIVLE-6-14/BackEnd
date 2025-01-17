package com.example.AISafety.domain.dashboard.service;

import com.example.AISafety.domain.dashboard.dto.BarDTO;
import com.example.AISafety.domain.dashboard.dto.LineDTO;
import com.example.AISafety.domain.dashboard.dto.MapDTO;
import com.example.AISafety.domain.dashboard.repository.DashboardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final DashboardRepository dashboardRepository;

    public List<BarDTO> getBarData() {
        // 현재 시간 기준으로 일주일 전 시작일과 종료일 계산
        LocalDateTime endDate = LocalDateTime.now();  // 현재 시간
        LocalDateTime startDate = endDate.minus(1, ChronoUnit.WEEKS); // 일주일 전

        // 끝 시간을 23:59:59로 설정
        endDate = endDate.withHour(23).withMinute(59).withSecond(59).withNano(0);

        // 시작 시간을 00:00:00으로 설정 (선택 사항, 현재는 이미 00:00:00으로 설정됨)
        startDate = startDate.withHour(0).withMinute(0).withSecond(0).withNano(0);
        // Repository에 날짜 범위 전달
        return dashboardRepository.findAnimalDistribution(startDate, endDate);
    }

    public List<MapDTO> getMapData(){
        return dashboardRepository.findAnimal();
    }

    public List<LineDTO> getLineData(){
        return dashboardRepository.getFollowUpCountsByDate();
    }
}
