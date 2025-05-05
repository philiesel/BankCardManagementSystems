package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_id_seq"
    )
    @SequenceGenerator(
            name = "users_id_seq",
            sequenceName = "users_id_seq",
            allocationSize = 1
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole role;
    @Column(unique = true, nullable = false)
    @NotNull(message = "Email пользователя обязательно")
    private String email;
    private String username;
    @Column(nullable = false)
    @NotNull(message = "Пароль пользователя обязательно")
    private String password;
    @OneToMany(mappedBy = "user")
    private List<CardEntity> cards;
 }
