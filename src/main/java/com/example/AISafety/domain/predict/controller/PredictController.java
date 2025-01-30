package com.example.AISafety.domain.predict.controller;

import com.example.AISafety.domain.predict.dto.PredictRiskRequestDTO;
import com.example.AISafety.domain.predict.dto.PredictRiskResponseDTO;
import com.example.AISafety.domain.predict.service.PredictService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/predict")
public class PredictController {
    private final PredictService predictService;
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ROAD_USER')")
    public ResponseEntity<Map<String, String>> saveTest(@RequestBody PredictRiskRequestDTO predictRiskRequestDTO){
        predictService.saveTest(predictRiskRequestDTO);
        Map<String,String> response = new HashMap<>();
        response.put("message", "저장 성공");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ROAD_USER')")
    public ResponseEntity<Map<String,Object>> getPredicts(){
       Map<String, Object> response = new HashMap<>();

       response.put("SUCCESS", "위험 도로 예측 결과 불러오기 성공");
        List<PredictRiskResponseDTO> predicts = predictService.getPredicts();
        response.put("message", predicts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
