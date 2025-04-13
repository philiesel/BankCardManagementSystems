package com.example.demo.exceptions;

public class NegativeValueSaveBanknote extends RuntimeException {
    public NegativeValueSaveBanknote (String message) {
        super(message);
    }
}
