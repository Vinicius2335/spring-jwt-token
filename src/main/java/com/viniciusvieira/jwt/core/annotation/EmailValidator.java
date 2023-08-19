package com.viniciusvieira.jwt.core.annotation;

import com.viniciusvieira.jwt.domain.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailValidator implements ConstraintValidator<EmailValidation, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        boolean emailIsInUse = userRepository.existsByEmail(email);
        return !emailIsInUse;
    }
}
