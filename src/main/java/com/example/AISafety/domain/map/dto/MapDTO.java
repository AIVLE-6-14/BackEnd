package com.example.AISafety.domain.map.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MapDTO {
    private String cctvUrl;
    private double longitude;
    private double latitude;
    private String cctvName;
}
