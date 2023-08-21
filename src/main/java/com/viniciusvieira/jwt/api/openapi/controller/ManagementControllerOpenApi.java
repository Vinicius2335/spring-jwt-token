package com.viniciusvieira.jwt.api.openapi.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Management")
@SecurityRequirement(name = "bearerAuth")
public interface ManagementControllerOpenApi {
    @Operation(
            description = "Get endpoint for manager",
            summary = "This is a summaru for management get endpoint",
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
            description = "Post endpoint for manager",
            summary = "This is a summaru for management post endpoint",
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
    @Hidden // para esconder o endpoint no swagger - a nivel de método ou classe
     String post();

    @Operation(
            description = "Put endpoint for manager",
            summary = "This is a summaru for management put endpoint",
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
            description = "Put endpoint for manager",
            summary = "This is a summary for management put endpoint",
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
}
