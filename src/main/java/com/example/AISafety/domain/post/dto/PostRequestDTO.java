package com.example.AISafety.domain.post.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class PostRequestDTO {

    private Long animalId;
    private String title;
    private String content;
}
