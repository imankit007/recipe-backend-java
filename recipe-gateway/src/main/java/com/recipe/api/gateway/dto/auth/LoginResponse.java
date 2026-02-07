package com.recipe.api.gateway.dto.auth;

public record LoginResponse(
        String accessToken,
        Long expiresIn
) {
}
