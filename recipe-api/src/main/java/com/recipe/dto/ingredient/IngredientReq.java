package com.recipe.dto.ingredient;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema
public record IngredientReq(
        @Schema(description = "Name of the Ingredient") @NotNull @NotEmpty String name
) {
}
