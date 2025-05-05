package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Table
@Entity
public class CardLimit {
    @Id
    private Long id;
    private BigDecimal dayOfLimit;
    private BigDecimal monthOfLimit;

    public CardLimit() {
        this.dayOfLimit = new BigDecimal("300");
        this.monthOfLimit = new BigDecimal("700");
    }
}
