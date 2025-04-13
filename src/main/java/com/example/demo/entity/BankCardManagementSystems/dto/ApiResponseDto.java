package com.example.demo.entity.BankCardManagementSystems.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseDto <T> {
    private boolean isSuccess;
    private String message;
}
