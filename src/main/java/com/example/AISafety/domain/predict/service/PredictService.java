package com.example.AISafety.domain.predict.service;

import com.example.AISafety.domain.animal.Animal;
import com.example.AISafety.domain.animal.service.AnimalService;
import com.example.AISafety.domain.predict.Predict;
import com.example.AISafety.domain.predict.PredictPossibility;
import com.example.AISafety.domain.predict.dto.PredictRiskRequestDTO;
import com.example.AISafety.domain.predict.dto.PredictRiskResponseDTO;
import com.example.AISafety.domain.predict.repository.PredictRepository;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PredictService {
    private final PredictRepository predictRepository;
    private final AnimalService animalService;
    private final WebClient webClient;

    //추후에 yaml 로 뺄것
    public void savePredict(Long animalId) {
        Animal animal = animalService.getAnimal(animalId);
        double latitude = animal.getLatitude();
        double longitude = animal.getLongitude();

        // WebClient로 데이터 요청
        Mono<PredictRiskRequestDTO> getPredict = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/predict")
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .build())
                .retrieve()
                .bodyToMono(PredictRiskRequestDTO.class)
                .onErrorResume(e -> {
                    // 예외가 발생한 경우 처리할 코드
                    return Mono.empty();  // 예외 발생 시 빈 Mono를 반환
                });

        // 결과 처리
        getPredict.subscribe(predictRiskRequestDTO -> {
            // 예측 결과를 Predict 객체로 변환
            Predict predict = new Predict();

            predict.setLongitude(longitude);
            predict.setLatitude(latitude);
            predict.setPredictClass(predictRiskRequestDTO.getRoadkillRisk());

            // PredictPossibility에 위험도 확률을 매핑
            PredictPossibility possibility = predictRiskRequestDTO.predictPossibilityDtoTo(predictRiskRequestDTO.getRiskLevelProbabilities());
            predict.setPredictPossibility(possibility);

            // 예측 데이터를 DB에 저장
            predictRepository.save(predict);

        });
    }


    public void saveTest(PredictRiskRequestDTO predictRiskRequestDTO){
        Predict predict = new Predict();

        predict.setLatitude(predictRiskRequestDTO.getLatitude());
        predict.setLongitude(predictRiskRequestDTO.getLongitude());
        predict.setPredictClass(predictRiskRequestDTO.getRoadkillRisk());
        predict.setPredictPossibility(
                predictRiskRequestDTO.predictPossibilityDtoTo(predictRiskRequestDTO.getRiskLevelProbabilities()));
        predictRepository.save(predict);
    }

    public List<PredictRiskResponseDTO> getPredicts() {
        List<Predict> predictList = predictRepository.findAll();

        return predictList.stream()
                .map(predict -> {
                    // PredictPossibility 객체 가져오기
                    PredictPossibility predictPossibility = predict.getPredictPossibility();

                    // riskLevelProbabilities 맵 생성
                    Map<String, String> riskLevelProbabilities = Map.of(
                            "Risk Level 1", Optional.ofNullable(predictPossibility.getRisk1()).orElse("0.0"),
                            "Risk Level 2", Optional.ofNullable(predictPossibility.getRisk2()).orElse("0.0"),
                            "Risk Level 3", Optional.ofNullable(predictPossibility.getRisk3()).orElse("0.0"),
                            "Risk Level 4", Optional.ofNullable(predictPossibility.getRisk4()).orElse("0.0")
                    );

                    // PredictRiskResponseDTO 반환
                    return new PredictRiskResponseDTO(
                            predict.getLatitude(),
                            predict.getLongitude(),
                            predict.getPredictClass(),
                            riskLevelProbabilities
                    );
                })
                .collect(Collectors.toList());
    }

}
