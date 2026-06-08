package com.recipe.web.model;

public record LoginResponse (
        String access_token,
        Long expiresIn
){}
