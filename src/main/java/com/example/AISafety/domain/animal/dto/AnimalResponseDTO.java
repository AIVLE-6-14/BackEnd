package com.example.AISafety.domain.animal.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AnimalResponseDTO {
    private final Long id;
    private final String name;
    private final double latitude;
    private final double longitude;
    private final LocalDateTime detectedAt;
}
