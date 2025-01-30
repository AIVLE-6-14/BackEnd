package com.example.AISafety.domain.predict.dto;

import com.example.AISafety.domain.predict.PredictPossibility;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PredictRiskResponseDTO {
    private double latitude;
    private double longitude;
    private Integer roadkillRisk;  // 예측된 위험도 (1, 2, 3, 4)
    private Map<String, String> riskLevelProbabilities;  // 위험도별 확률 맵


    public PredictPossibility predictPossibilityDtoTo(Map<String,String> possible){
        PredictPossibility possibility = new PredictPossibility();

        // "Risk Level" 키에 따른 값을 String 타입으로 설정
        for (Map.Entry<String, String> entry : riskLevelProbabilities.entrySet()) {
            switch (entry.getKey()) {
                case "Risk Level 1":
                    possibility.setRisk1(entry.getValue());  // String으로 그대로 저장
                    break;
                case "Risk Level 2":
                    possibility.setRisk2(entry.getValue());  // String으로 그대로 저장
                    break;
                case "Risk Level 3":
                    possibility.setRisk3(entry.getValue());  // String으로 그대로 저장
                    break;
                case "Risk Level 4":
                    possibility.setRisk4(entry.getValue());  // String으로 그대로 저장
                    break;
            }
        }
        return possibility;
    }

}
