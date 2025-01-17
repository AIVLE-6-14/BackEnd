package com.example.AISafety.domain.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnimalDashboardDTO {
    private final String name; // id를 제거하고 name만 포함
    private final Long count;  // 카운트만 포함

    public AnimalDashboardDTO(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
