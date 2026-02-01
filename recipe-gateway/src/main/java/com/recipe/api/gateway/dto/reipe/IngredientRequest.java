package com.recipe.api.gateway.dto.reipe;

import java.math.BigDecimal;

public record IngredientRequest(
        Long ingredientId,
        String name,
        BigDecimal quantity,
        String unit
) {
}
