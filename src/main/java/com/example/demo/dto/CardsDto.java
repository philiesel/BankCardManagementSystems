package com.example.demo.dto;

import com.example.demo.entity.CardEntity;
import com.example.demo.entity.CardStatus;
import com.example.demo.entity.TransactionEntity;
import com.example.demo.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardsDto {
    private Integer cardNumber;
    private String cardholderName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate expiryDate;
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    private BigDecimal balance;

    public CardsDto(CardEntity cardEntity) {
        this.cardNumber = cardEntity.getCardNumber();
        this.cardholderName = cardEntity.getCardholderName();
        this.expiryDate = cardEntity.getExpiryDate();
        this.status = cardEntity.getStatus();
        this.balance = cardEntity.getBalance();
    }
}
