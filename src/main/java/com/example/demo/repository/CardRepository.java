package com.example.demo.repository;

import com.example.demo.entity.CardEntity;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository <CardEntity, Long> {
    List<CardEntity> findByUser(User user);
}

