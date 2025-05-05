package com.example.demo.repository;

import com.example.demo.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionLimit extends JpaRepository<TransactionEntity, Long> {
    // запрос суммы за день
    // запрос сумму за месяц
    // или 1 запрос за выбранный период
}
