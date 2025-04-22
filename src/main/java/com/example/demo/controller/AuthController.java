package com.example.demo.controller;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SingUpRequestDto;
import com.example.demo.service.JwtAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {
    private final JwtAuthenticationService jwtService;

    @PostMapping("/registration")
    public JwtAuthenticationResponse registrationUser(@RequestBody SingUpRequestDto authDto) {
        return jwtService.jwtAuthentication(authDto);
    }

}
