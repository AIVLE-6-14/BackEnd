package com.example.AISafety.domain.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BarDTO {
    private  String name; // id를 제거하고 name만 포함
    private  Long count;  // 카운트만 포함


}
