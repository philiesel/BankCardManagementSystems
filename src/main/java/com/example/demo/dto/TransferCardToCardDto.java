package com.example.demo.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferCardToCardDto {
    private Long fromCardId;
    private Long toCardId;
    private BigDecimal amount;
}
