package com.recipe.api.gateway.dto.reipe;

public record RecipeStep(
        Integer stepNumber,
        String instruction,
        Integer durationMinutes,
        String mediaUrl
) {
}
