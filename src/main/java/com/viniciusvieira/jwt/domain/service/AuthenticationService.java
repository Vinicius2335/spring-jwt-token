package com.viniciusvieira.jwt.domain.service;

import com.viniciusvieira.jwt.api.representation.model.request.AuthenticationRequest;
import com.viniciusvieira.jwt.api.representation.model.request.RegisterRequest;
import com.viniciusvieira.jwt.api.representation.model.response.AuthenticationResponse;
import com.viniciusvieira.jwt.core.security.service.JwtService;
import com.viniciusvieira.jwt.domain.model.Role;
import com.viniciusvieira.jwt.domain.model.User;
import com.viniciusvieira.jwt.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodePassword(request.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);
        Map claims = new HashMap();
        claims.put("role", user.getRole());

        String jwtToken = jwtService.generateToken(claims, user);
        //String jwtToken = jwtService.generateToken(user)
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }
}
