package com.recipe.dto.auth;

public record LoginResponse(
        String accessToken,
        Long expiresIn
) {
}
