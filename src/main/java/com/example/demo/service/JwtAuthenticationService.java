package com.example.demo.service;

import com.example.demo.dto.JwtAuthenticationResponse;
import com.example.demo.dto.SingUpRequestDto;
import com.example.demo.entity.ERole;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JwtAuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse jwtAuthentication(SingUpRequestDto request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UsernameNotFoundException("Пользователь с email " + request.getEmail() + " уже зарегистрирован");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(ERole.ROLE_USER)
                .build();
        userRepository.save(user);
        String jwtToken = jwtService.generateToken(new MyUserDetails(user));
        return new JwtAuthenticationResponse(jwtToken);
    }
}
