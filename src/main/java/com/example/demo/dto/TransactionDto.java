package com.example.demo.dto;

import com.example.demo.entity.CardEntity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private CardEntity fromcard;
    private CardEntity toCard;
    private BigDecimal amountTransfer;
    private String status;
    private LocalDateTime timestamp;
}
