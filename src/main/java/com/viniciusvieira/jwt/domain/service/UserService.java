package com.viniciusvieira.jwt.domain.service;

import com.viniciusvieira.jwt.api.representation.model.request.ChangePasswordRequest;
import com.viniciusvieira.jwt.domain.model.user.User;
import com.viniciusvieira.jwt.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // verificando se o password atual está correto
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new IllegalStateException("Wrong password!");
        }

        // verificando se a nova senha é igual a senha de confirmação
        if (!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Password are not the same!");
        }

        // atualizando a senha
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // atualizando o usuário
        userRepository.save(user);
    }
}
