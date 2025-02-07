package com.example.AISafety.domain.predict.dto;

import com.example.AISafety.domain.predict.Dangerous;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("예측된 발생건수(5km)")  // JSON에서 오는 필드 이름과 매핑
    private double predictCnt;

    @JsonProperty("위험도")  // JSON에서 오는 필드 이름과 매핑
    private Dangerous dangerous;


}
