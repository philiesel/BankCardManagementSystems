package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CardStatusTransactionDto {
    private String fromCard;
    private String toCard;
    private String amountTransfer;
    private String status;
    private LocalDate date;
}
