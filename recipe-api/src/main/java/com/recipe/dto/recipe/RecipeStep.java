package com.recipe.dto.recipe;

public record RecipeStep(
        Integer stepNumber,
        String instruction,
        Integer durationMinutes,
        String mediaUrl
) {
}
