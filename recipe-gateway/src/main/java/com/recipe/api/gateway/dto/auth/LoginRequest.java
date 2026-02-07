package com.recipe.api.gateway.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Login request containing username and password")
public record LoginRequest(
        @NotNull String username,
        @NotNull String password
) {
}
