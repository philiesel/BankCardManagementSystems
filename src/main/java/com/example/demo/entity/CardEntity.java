package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class CardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer cardNumber;
    private String cardholderName;
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate expiryDate;
    @Enumerated(EnumType.STRING)
    private CardStatus status;
    private BigDecimal balance;
    //  private List<TransactionEntity> transactions;
}
