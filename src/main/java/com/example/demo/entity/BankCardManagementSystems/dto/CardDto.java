package com.example.demo.entity.BankCardManagementSystems.dto;

import com.example.demo.entity.BankCardManagementSystems.CardStatus;

public record CardDto(
        Integer cardNumber, CardStatus status
) { }
