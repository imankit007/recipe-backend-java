package com.recipe.api.gateway.dto.ingredient;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response for Create Ingredient")
public record IngredientResponse(
        Long id,
        String name) {
}
