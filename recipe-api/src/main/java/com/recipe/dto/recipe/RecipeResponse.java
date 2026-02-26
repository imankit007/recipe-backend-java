package com.recipe.dto.recipe;

//import com.recipe.core.enums.RecipeDifficultyLevel;

import com.recipe.core.enums.RecipeDifficultyLevel;

public record RecipeResponse(
        Long id,
        String name,
        RecipeDifficultyLevel difficulty,
        Integer prepTimeMinutes,
        Integer cookTimeMinutes,
        Integer servings,
        String description,
        java.util.List<RecipeIngredient> ingredients,
        java.util.List<RecipeStep> steps
) {
    public static final RecipeResponse EMPTY = new RecipeResponse(
            null, null, null, null,
            null,
            null, null, null, null
    );

}