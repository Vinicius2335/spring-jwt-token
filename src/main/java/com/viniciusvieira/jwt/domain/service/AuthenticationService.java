package com.viniciusvieira.jwt.domain.service;

import com.viniciusvieira.jwt.api.representation.model.request.AuthenticationRequest;
import com.viniciusvieira.jwt.api.representation.model.request.RegisterRequest;
import com.viniciusvieira.jwt.api.representation.model.response.AuthenticationResponse;
import com.viniciusvieira.jwt.core.security.service.JwtService;
import com.viniciusvieira.jwt.domain.model.token.TokenModel;
import com.viniciusvieira.jwt.domain.model.token.TokenType;
import com.viniciusvieira.jwt.domain.model.user.Role;
import com.viniciusvieira.jwt.domain.model.user.User;
import com.viniciusvieira.jwt.domain.repository.TokenModelRepository;
import com.viniciusvieira.jwt.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenModelRepository tokenModelRepository;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(encodePassword(request.getPassword()))
                .role(Role.USER)
                .build();

        User savedUser = userRepository.save(user);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", user.getRole());

        String jwtToken = jwtService.generateToken(claims, user);
        //String jwtToken = jwtService.generateToken(user)

        savedUserToken(jwtToken, savedUser);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    // toda vez que realizamos o 'login', geramos um novo token.
    // e precisamos expirar/revogar todas os outros tokens anteriores, caso nao esteja, do usuário.
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

        revokedAllUserTokens(user);
        savedUserToken(jwtToken, user);

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    private String encodePassword(String password){
        return passwordEncoder.encode(password);
    }

    private void savedUserToken(String jwtToken, User savedUser) {
        TokenModel tokenModel = TokenModel.builder()
                .token(jwtToken)
                .user(savedUser)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenModelRepository.save(tokenModel);
    }

    // Revogando Todos os tokens do usuário
    private void revokedAllUserTokens(User user){
        List<TokenModel> validsUserTokens = tokenModelRepository.findAllValidTokensByUser(user.getId());
        if (validsUserTokens.isEmpty()){
            // se o usuário nao possui nenhum token registrado, retorna sem fazer nada
            return;
        }

        validsUserTokens.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenModelRepository.saveAll(validsUserTokens);
    }
}
