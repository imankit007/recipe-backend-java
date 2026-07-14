package com.recipe.web.model.user;

public record PresignResponse(String uploadUrl, String objectKey, int expiresInSeconds) {}