package com.example.demo.dto;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String username;
    private String password;
    private String email;
    // TODO role добавить
}
