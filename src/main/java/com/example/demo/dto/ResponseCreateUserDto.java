package com.example.demo.dto;

import com.example.demo.entity.User;
import lombok.Data;

@Data
public class ResponseCreateUserDto {
    private String username;
    private String email;
    private String role;
    public ResponseCreateUserDto(User user) {
        username = user.getUsername();
        email = user.getEmail();
        role = String.valueOf(user.getRole());
    }
}
