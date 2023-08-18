package com.viniciusvieira.jwt.api.controller;

import com.viniciusvieira.jwt.api.representation.model.request.AuthenticationRequest;
import com.viniciusvieira.jwt.api.representation.model.request.RegisterRequest;
import com.viniciusvieira.jwt.api.representation.model.response.AuthenticationResponse;
import com.viniciusvieira.jwt.domain.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    // FIXME - caso ocorra qualquer erro, ele lança 403 forbidden
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(){
        return ResponseEntity.ok("Hello");
    }
}
