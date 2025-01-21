package com.example.AISafety.global.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponse {
    @JsonProperty("FAIL")
    private String fail;
    private String message;
    private String status;
}
