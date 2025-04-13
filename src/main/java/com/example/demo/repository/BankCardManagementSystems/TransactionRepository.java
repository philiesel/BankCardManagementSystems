package com.example.demo.repository.BankCardManagementSystems;

import com.example.demo.entity.BankCardManagementSystems.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
