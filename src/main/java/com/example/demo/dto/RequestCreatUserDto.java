package com.example.demo.dto;

import lombok.Data;

@Data
public class RequestCreatUserDto {
    private String email;
    private String password;
    private String role;
    private String username;
}
