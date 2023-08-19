package com.viniciusvieira.jwt.core.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { EmailValidator.class })
// é essa classe que fica em cima do atributo na classe
public @interface EmailValidation {
    String message() default "Invalid Email: Email already in registered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
