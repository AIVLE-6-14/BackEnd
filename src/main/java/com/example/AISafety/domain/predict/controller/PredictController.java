package com.example.AISafety.domain.predict.controller;

import com.example.AISafety.domain.predict.dto.PredictRiskRequestDTO;
import com.example.AISafety.domain.predict.dto.PredictRiskResponseDTO;
import com.example.AISafety.domain.predict.service.PredictService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name="Animal API", description = "예측모델 관련 API 를 제공합니다.")
public class PredictController {
    private final PredictService predictService;
    @PostMapping()
    @PreAuthorize("hasRole('ROLE_ROAD_USER')")
    @Operation(summary="예측 모델 결과값 테이블 저장", description = "태스트 용으로 예측모델의 값을 저장 할 수 있습니다.")
    public ResponseEntity<Map<String, String>> saveTest(@RequestBody PredictRiskRequestDTO predictRiskRequestDTO){
        predictService.saveTest(predictRiskRequestDTO);
        Map<String,String> response = new HashMap<>();
        response.put("message", "저장 성공");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ROLE_ROAD_USER') or hasRole('ROLE_SAFETY')")
    @Operation(summary="예축 모델 결과 조회", description = "예측 모델이 판단한 도로의 위험도 리스트를 조회 할 수 있습니다.")
    public ResponseEntity<Map<String,Object>> getPredicts(){
       Map<String, Object> response = new HashMap<>();

       response.put("SUCCESS", "위험 도로 예측 결과 불러오기 성공");
        List<PredictRiskResponseDTO> predicts = predictService.getPredicts();
        response.put("message", predicts);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
