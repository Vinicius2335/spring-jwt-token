package com.viniciusvieira.jwt.api.controller;

import com.viniciusvieira.jwt.api.openapi.controller.UserControllerOpenApi;
import com.viniciusvieira.jwt.api.representation.model.request.ChangePasswordRequest;
import com.viniciusvieira.jwt.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class UserController implements UserControllerOpenApi {
    private final UserService userService;

    @GetMapping
    public String get(){
        return "GET: user controller";
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:create')")
    public String post(){
        return "POST: user controller";
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:update')")
    public String put(){
        return "PUT: user controller";
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:delete')")
    public String delete(){
        return "DELETE: user controller";
    }

    @PatchMapping
    public ResponseEntity<Void> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }
}
