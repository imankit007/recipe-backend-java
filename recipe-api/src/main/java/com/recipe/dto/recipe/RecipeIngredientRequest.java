package com.recipe.dto.recipe;

import java.math.BigDecimal;


public record RecipeIngredientRequest(
        Long ingredientId,
        BigDecimal quantity,
        String unit
) {
}
