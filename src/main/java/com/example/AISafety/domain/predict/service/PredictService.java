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
    private String url = "http://localhost:3000/predict";
    public void savePredict(Long animalId)
    {
        Animal animal = animalService.getAnimal(animalId);
        double latitude = animal.getLatitude();
        double longitude = animal.getLongitude();

        // 추후에 YOLO 도 webClient 로 적용할 것
        Mono<PredictRiskRequestDTO> getPredict = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(url)
                        .queryParam("lat",latitude)
                        .queryParam("lon",longitude)
                        .build())
                .retrieve()
                .bodyToMono(PredictRiskRequestDTO.class);

        getPredict.subscribe(predictRiskRequestDTO ->{
            Predict predict = new Predict();

            predict.setLongitude(longitude);
            predict.setLatitude(latitude);
            predict.setPredictClass(predictRiskRequestDTO.getRoadkillRisk());
            predict.setPredictPossibility(
                    predictRiskRequestDTO.predictPossibilityDtoTo(predictRiskRequestDTO.getRiskLevelProbabilities()));

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
