package com.viniciusvieira.jwt.api.openapi.controller;

import com.viniciusvieira.jwt.api.representation.model.request.ChangePasswordRequest;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

@Tag(name = "User")
@SecurityRequirement(name = "bearerAuth")
public interface UserControllerOpenApi {
    @Operation(
            description = "Get endpoint for user",
            summary = "This is a summaru for user get endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    String get();

    @Operation(
            description = "Post endpoint for user",
            summary = "This is a summary for user post endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @Hidden
    String post();

    @Operation(
            description = "Put endpoint for user",
            summary = "This is a summary for user put endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    String put();

    @Operation(
            description = "Put endpoint for user",
            summary = "This is a summary for user put endpoint",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    String delete();

    @Operation(
            description = "Patch endpoint for user",
            summary = "Endpoint responsible for changing the user's password",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    ResponseEntity<Void> changePassword(
            @RequestBody(description = "Representation of a new password", required = true) ChangePasswordRequest request,
            Principal connectedUser
    );
}
