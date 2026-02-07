package com.recipe.api.gateway.dto.reipe;

import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;


@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public record RecipeIngredientRequest(
        Long ingredientId,
        BigDecimal quantity,
        String unit
) {
}
