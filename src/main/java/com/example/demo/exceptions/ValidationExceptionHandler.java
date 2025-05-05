package com.example.demo.exceptions;

import com.example.demo.dto.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(UserWithEmailAlreadyExists.class)
    public ResponseEntity<ErrorResponse> ErrorEmailAlreadyExist(UserWithEmailAlreadyExists err) {
        //ErrorResponse error = ErrorResponse.create(err, HttpStatus.CONFLICT, err.getMessage());
        ErrorResponse error = ErrorResponse.builder()
                .status(409)
                .message("Укажите другой email")
                .error("Вот")
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}