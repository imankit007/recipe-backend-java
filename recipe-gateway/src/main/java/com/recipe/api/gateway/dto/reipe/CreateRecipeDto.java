package com.recipe.api.gateway.dto.reipe;


import com.fasterxml.jackson.annotation.JacksonAnnotation;
import lombok.Builder;
import lombok.Data;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;


public record CreateRecipeDto(
        String title,
        String description,
        Integer prepTimeMinutes,
        Integer cookTimeMinutes,
        Integer servings,
        Integer difficultyLevel


) {
}
