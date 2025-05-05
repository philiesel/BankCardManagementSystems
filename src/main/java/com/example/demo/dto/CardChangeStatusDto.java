package com.example.demo.dto;

import com.example.demo.entity.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CardChangeStatusDto {
    private final Integer cardNumber;
    private final CardStatus status;
}
