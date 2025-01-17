package com.example.AISafety.domain.dashboard.controller;

import com.example.AISafety.domain.dashboard.dto.BarDTO;
import com.example.AISafety.domain.dashboard.dto.LineDTO;
import com.example.AISafety.domain.dashboard.dto.MapDTO;
import com.example.AISafety.domain.dashboard.service.DashboardService;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dashBoard")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/bar")
    public ResponseEntity<Map<String,Object>> getBarData() {
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS","BarChart 데이터 불러오기 성공");
        List<BarDTO> animalDistribution = dashboardService.getBarData();
        response.put("message", animalDistribution);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/map-position")
    public ResponseEntity<Map<String,Object>> getMapData(){
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS", "Map Animal 위치 불러오기 성공");
        List<MapDTO> animalLangLong = dashboardService.getMapData();
        response.put("message", animalLangLong);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/line")
    public ResponseEntity<Map<String,Object>> getLineData(){
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS", "Line 데이터 불러오기 성공");
        List<LineDTO> lineData = dashboardService.getLineData();
        response.put("message", lineData);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
