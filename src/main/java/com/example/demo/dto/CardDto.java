package com.example.demo.dto;

import com.example.demo.entity.CardStatus;
import lombok.Data;

@Data
public class CardDto {
    public CardDto(Integer cardNumber, CardStatus status) {
    }
}
