package com.recipe.api.gateway.dto.reipe;

import com.recipe.core.enums.RecipeDifficultyLevel;

public record RecipeResponse(Long id, String name, RecipeDifficultyLevel difficulty) {
}