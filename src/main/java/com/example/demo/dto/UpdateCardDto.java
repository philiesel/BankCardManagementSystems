package com.example.demo.dto;

import com.example.demo.entity.CardEntity;
import com.example.demo.entity.CardStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class UpdateCardDto {
    public UpdateCardDto(CardEntity cardEntity) {
        cardNumber = cardEntity.getCardNumber();
        cardholderName = cardEntity.getCardholderName();
        expiryDate = cardEntity.getExpiryDate();
        status = cardEntity.getStatus();
        balance = cardEntity.getBalance();
    }

    private Integer cardNumber;
    private String cardholderName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate expiryDate;
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    private BigDecimal balance;
}
