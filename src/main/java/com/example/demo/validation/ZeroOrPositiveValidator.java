package com.example.demo.validation;

import com.example.demo.annotation.ZeroOrPositive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;

public class ZeroOrPositiveValidator implements ConstraintValidator<ZeroOrPositive, BigDecimal> {
    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext constraintValidatorContext) {
       if (value == null) {
           return false;
       }
       return value.compareTo(BigDecimal.ZERO) >= 0;
    }
}
