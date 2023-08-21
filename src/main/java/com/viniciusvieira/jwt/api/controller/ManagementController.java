package com.viniciusvieira.jwt.api.controller;

import com.viniciusvieira.jwt.api.openapi.controller.ManagementControllerOpenApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/management")
@RequiredArgsConstructor
public class ManagementController implements ManagementControllerOpenApi {
    @GetMapping
    public String get(){
        return "GET: management controller";
    }

    @PostMapping
    public String post(){
        return "POST: management controller";
    }

    @PutMapping
    public String put(){
        return "PUT: management controller";
    }

    @DeleteMapping
    public String delete(){
        return "DELETE: management controller";
    }
}
