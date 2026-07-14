package com.recipe.web.model.user;


public record UserResponse(Long userId, String email, String displayName, String profilePictureUrl, String profileThumbnailUrl) {}
