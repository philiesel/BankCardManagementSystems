package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.entity.CardEntity;
import com.example.demo.entity.User;
import com.example.demo.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    /**
     * Создать пользователя
     *
     * @return созданного пользователя
     */
    @PostMapping("users/add")
    public ResponseEntity<ResponseCreateUserDto> addUser(@Valid @RequestBody RequestCreatUserDto payload) {
        User createdUser = adminService.createUser(payload);
        ResponseCreateUserDto responseUser = new ResponseCreateUserDto(createdUser);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();
        return ResponseEntity.created(location).body(responseUser);
    }

    /**
     * Получение всех пользователей
     *
     * @return список всех пользователей
     */
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() { //TODO пагинация
        List<User> allUsers = adminService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    /**
     * Получение пользователея по email
     *
     * @return пользователь с данным email
     */
    @GetMapping("/users/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable @NotNull String email) {
        try {
            User user = adminService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UsernameNotFoundException e) {
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(format("Пользователь с %s не найден", email));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
        }
    }

    /**
     * Обновление пользователея
     *
     * @return пользователь с обновленными данными
     */
    @PatchMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UpdateUserDto updateUserDto) {
        User user = adminService.updateUser(id, updateUserDto);
        return ResponseEntity.ok().body(user);
    }

    /**
     * Удалить пользователея
     *
     * @return удаляемый пользователь
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        try {
            User deleteUser = adminService.deleteUser(id);
            return ResponseEntity.status(HttpStatus.OK).body(deleteUser);
        } catch (UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}