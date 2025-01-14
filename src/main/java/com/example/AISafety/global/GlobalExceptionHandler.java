package com.example.AISafety.global;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    //IllegalArgumentException 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> handleIllegalArgumentException(IllegalArgumentException ex){
        Map<String, String> response = new HashMap<>();
        response.put("ERROR", "error");
        response.put("Message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //EntityNotFoundException 예외 처리
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String,String>> handleEntityNotFoundException(EntityNotFoundException ex){
        Map<String, String> response = new HashMap<>();
        response.put("ERROR", "error");
        response.put("Message", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
