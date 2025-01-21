package com.example.AISafety.global.error;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.EntityNotFoundException;
import java.nio.file.AccessDeniedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
@Hidden
@RestControllerAdvice
public class GlobalExceptionHandler {

    //IllegalArgumentException 예외 처리
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException ex){
       ErrorResponse errorResponse = new ErrorResponse("FAIL", "허용 되지 않는 argument 가 들어왔습니다.",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    //EntityNotFoundException 예외 처리
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex){
        ErrorResponse errorResponse = new ErrorResponse("FAIL", "해당 Entity를 찾을 수 없습니다.",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    // 중복 값 예외 처리(데이터 무결성)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        ErrorResponse errorResponse = new ErrorResponse("FAIL","중복된 데이터 처리 요청이 발생했습니다.",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }
    // 권한 거부 예외 처리 (403 Forbidden)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        ErrorResponse errorResponse = new ErrorResponse("FAIL","권한이 없습니다.",ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

    // 인증 예외 처리 (401 Unauthorized)
    @ExceptionHandler(AuthenticationServiceException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse("FAIL","인증에 실패했습니다.", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.OK);
    }

}
