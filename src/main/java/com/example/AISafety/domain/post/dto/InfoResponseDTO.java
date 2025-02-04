package com.example.AISafety.domain.post.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfoResponseDTO {
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> d9774d4 (fix : LLM 사용을 위해 기본 정보 넘겨주는 로직 작성)

    @JsonProperty("detected_time")
    private LocalDateTime detectedTime;

=======
    @JsonProperty("incident_time")
    private LocalDateTime incidentTime;
>>>>>>> f1d5c5b (fix : LLM 사용을 위해 기본 정보 넘겨주는 로직 작성)
<<<<<<< HEAD
=======
    @JsonProperty("detected_time")
    private LocalDateTime detectedTime;
>>>>>>> 140ef00 (fix : dto 변경)
=======
>>>>>>> d9774d4 (fix : LLM 사용을 위해 기본 정보 넘겨주는 로직 작성)

    private double latitude;

    private double longitude;

    private String department;

    @JsonProperty("animal_name")
    private String animalName;

    private String responder;
}
