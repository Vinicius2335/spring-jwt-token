package com.viniciusvieira.jwt.api.openapi.controller;

import com.viniciusvieira.jwt.api.representation.model.request.AuthenticationRequest;
import com.viniciusvieira.jwt.api.representation.model.request.RegisterRequest;
import com.viniciusvieira.jwt.api.representation.model.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication")
public interface AuthenticationControllerOpenApi {

    @Operation(
            summary = "Save new User",
            description = "Insert user in the database, and return a valid token",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    ResponseEntity<AuthenticationResponse> register(@RequestBody(description = "Representation of a new user", required = true) RegisterRequest request);

    @Operation(
            summary = "Authenticate User",
            description = "checks if the user exists, and return a new valid token",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    ResponseEntity<AuthenticationResponse> authenticate(@org.springframework.web.bind.annotation.RequestBody AuthenticationRequest request);

    @Operation(
            summary = "Say hello to the authenticated user",
            description = "Return a string",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    )
            }
    )
    ResponseEntity<String> hello();
}
