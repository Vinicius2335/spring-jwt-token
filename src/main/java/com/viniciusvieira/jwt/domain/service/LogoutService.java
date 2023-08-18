package com.viniciusvieira.jwt.domain.service;

import com.viniciusvieira.jwt.domain.model.token.TokenModel;
import com.viniciusvieira.jwt.domain.repository.TokenModelRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {
    private final TokenModelRepository tokenModelRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        jwt = authHeader.substring(7);
        Optional<TokenModel> storedTokenOpt = tokenModelRepository.findByToken(jwt);
        if (storedTokenOpt.isPresent()){
            TokenModel storedToken = storedTokenOpt.get();
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenModelRepository.save(storedToken);
        }

    }
}
