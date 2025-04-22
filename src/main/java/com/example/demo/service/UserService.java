package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.MyUserDetailService;
import com.example.demo.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User createUser(User user) {
        String email = user.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new UserAlreadyExistsException("Пользователь с таким именем уже существует: " + user);
        }
        return userRepository.save(user);
    }

    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

}
