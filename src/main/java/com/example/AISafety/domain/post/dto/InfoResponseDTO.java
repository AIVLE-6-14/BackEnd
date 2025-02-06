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


    private double latitude;

    private double longitude;

    private String department;

    private String responder;

    @JsonProperty("detected_time")
    private LocalDateTime detectedTime;

    @JsonProperty("animal_name")
    private String animalName;


}
