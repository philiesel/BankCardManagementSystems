package com.example.demo.annotation;

import com.example.demo.validation.ZeroOrPositiveValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ZeroOrPositiveValidator.class)
public @interface ZeroOrPositive {
    String message() default "Значение должно быть положительное или ноль";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
