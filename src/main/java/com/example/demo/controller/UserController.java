package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.exceptions.UserAlreadyExistsException;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    @ExceptionHandler(UserAlreadyExistsException.class)
    public User createUser(@RequestBody  @Valid User user) {
        return userService.createUser(user);
    }

}
