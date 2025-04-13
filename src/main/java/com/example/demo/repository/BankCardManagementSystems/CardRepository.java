package com.example.demo.repository.BankCardManagementSystems;

import com.example.demo.entity.BankCardManagementSystems.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository <CardEntity, Long> {
}



