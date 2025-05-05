package com.example.demo.dto;

import com.example.demo.annotation.ZeroOrPositive;
import com.example.demo.entity.CardEntity;
import com.example.demo.entity.CardStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CreateCardDto {
    public CreateCardDto(CardEntity cardEntity) {
        userId = cardEntity.getId();
        cardNumber = cardEntity.getCardNumber();
        cardholderName = cardEntity.getCardholderName();
        expiryDate = cardEntity.getExpiryDate();
        balance = cardEntity.getBalance();
        cardStatus = cardEntity.getStatus();
    }
    @NotNull
    private Long userId;
    @NotNull
    private Integer cardNumber;
    @NotBlank
    private String cardholderName;
    @NotNull
    @Future
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate expiryDate;
    @NotNull
    @ZeroOrPositive
    private BigDecimal balance;
    @NotNull
    @Enumerated(EnumType.STRING)
    private CardStatus cardStatus = CardStatus.ACTIVE;
}
