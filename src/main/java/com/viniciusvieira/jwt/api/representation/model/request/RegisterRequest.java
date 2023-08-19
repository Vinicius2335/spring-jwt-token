package com.viniciusvieira.jwt.api.representation.model.request;

import com.viniciusvieira.jwt.core.annotation.EmailValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String firstName;
    private String lastName;

    @EmailValidation
    private String email;
    private String password;
}
