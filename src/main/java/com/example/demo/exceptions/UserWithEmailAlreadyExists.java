package com.example.demo.exceptions;

public class UserWithEmailAlreadyExists extends RuntimeException {
    public UserWithEmailAlreadyExists(String err) {
        super(err);
    }
}
