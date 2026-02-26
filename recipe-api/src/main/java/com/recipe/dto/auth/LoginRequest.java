package com.recipe.dto.auth;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Login request containing username and password")
public record LoginRequest(
        @NotNull @Schema(defaultValue = "ankit@gmail.com") String username,
        @NotNull @Schema(defaultValue = "aerohive") String password
) {
}
