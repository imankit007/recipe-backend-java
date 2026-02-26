package com.recipe.dto.recipe;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;


public record RecipeIngredientRequest(
        Long ingredientId,
        BigDecimal quantity,
        String unit
) {
}
