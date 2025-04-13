package com.example.demo.entity.BankCardManagementSystems;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ERole role;
    @Column(unique = true, nullable = false)
    private String email;
    private String username;
    @Column(nullable = false)
    private String password;
    private boolean enabled;
}
