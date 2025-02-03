package com.example.AISafety.domain.predict.dto;

import com.example.AISafety.domain.predict.PredictPossibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PredictRiskRequestDTO {
    private double latitude;
    private double longitude;
    @JsonProperty("roadkill_risk")  // JSON에서 오는 필드 이름과 매핑
    private String roadkillRisk;  // 예측된 위험도 (1, 2, 3, 4)

    @JsonProperty("risk_level_probabilities")  // JSON에서 오는 필드 이름과 매핑
    private Map<String, String> riskLevelProbabilities;  // 위험도별 확률 맵


    public PredictPossibility predictPossibilityDtoTo(Map<String, String> possible){
        PredictPossibility possibility = new PredictPossibility();

        // "Risk Level" 키에 따른 값을 String 타입으로 설정
        for (Map.Entry<String, String> entry : possible.entrySet()) {  // 여기서 'possible'을 사용해야 합니다.
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
