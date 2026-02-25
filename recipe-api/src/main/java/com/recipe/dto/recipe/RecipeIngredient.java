package com.recipe.dto.recipe;

public record RecipeIngredient(
        Long id,
        String name,
        Double quantity,
        String unit
){}
