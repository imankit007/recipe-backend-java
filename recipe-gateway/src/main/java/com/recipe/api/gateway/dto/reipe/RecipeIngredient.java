package com.recipe.api.gateway.dto.reipe;

public record RecipeIngredient(
        Long id,
        String name,
        Double quantity,
        String unit
){}
