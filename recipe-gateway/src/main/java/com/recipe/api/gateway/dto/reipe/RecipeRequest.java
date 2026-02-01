package com.recipe.api.gateway.dto.reipe;


import com.recipe.core.enums.RecipeDifficultyLevel;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record RecipeRequest(
        @NotNull
        String title,
        String description,
        Integer prepTimeMinutes,
        Integer cookTimeMinutes,
        @Min(1) Integer servings,
        @NotNull RecipeDifficultyLevel difficulty,
        @NotEmpty List<IngredientRequest> ingredients,
        @NotEmpty List<RecipeStep> steps
) {
}
