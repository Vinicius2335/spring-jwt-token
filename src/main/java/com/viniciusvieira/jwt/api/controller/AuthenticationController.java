package com.viniciusvieira.jwt.api.controller;

import com.viniciusvieira.jwt.api.representation.model.request.AuthenticationRequest;
import com.viniciusvieira.jwt.api.representation.model.request.RegisterRequest;
import com.viniciusvieira.jwt.api.representation.model.response.AuthenticationResponse;
import com.viniciusvieira.jwt.domain.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    // FIXME - caso ocorra qualquer erro, ele lança 403 forbidden
    private final AuthenticationService authenticationService;

    // aki é para se registrar
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody @Valid RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    // aki seria como se fosse o login da nossa aplicaçao
    // toda vez que realizamos o login geramos um novo token
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
}
