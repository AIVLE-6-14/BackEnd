package com.example.AISafety.domain.animal.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class AnimalDTO {
    private String name;
    private double latitude;
    private double longitude;
    private String imgUrl;
}
