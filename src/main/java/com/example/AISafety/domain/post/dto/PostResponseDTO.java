package com.example.AISafety.domain.post.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

    private Long id;
    private String organizationName;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
    private Long followupId;
}
