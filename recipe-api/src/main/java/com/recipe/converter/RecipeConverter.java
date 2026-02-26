package com.recipe.converter;


import com.recipe.data.jdbc.model.Recipe;
import com.recipe.dto.recipe.RecipeIngredient;
import com.recipe.dto.recipe.RecipeResponse;
import com.recipe.dto.recipe.RecipeStep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RecipeConverter {

    public RecipeResponse toRecipeResponse(Recipe recipe) {
        List<RecipeIngredient> ingredientList = new ArrayList<>();
        List<RecipeStep> stepList = new ArrayList<>();

        recipe.getIngredients().forEach( recipeIngredient -> {
            RecipeIngredient ingredient = new RecipeIngredient(
                    recipeIngredient.getId(),
                    recipeIngredient.getIngredient().getName(),
                    recipeIngredient.getQuantity().doubleValue(),
                    recipeIngredient.getUnit().toString()
            );
            ingredientList.add(ingredient);
        });

        recipe.getSteps().forEach( recipeStep -> {
            RecipeStep step = new RecipeStep(
                    recipeStep.getStepNumber(),
                    recipeStep.getInstruction(),
                    recipeStep.getDurationMinutes(),
                    recipeStep.getMediaUrl()
            );
            stepList.add(step);
        });

        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDifficultyLevel(),
                recipe.getPrepTimeMinutes(),
                recipe.getCookTimeMinutes(),
                recipe.getServings(),
                recipe.getDescription(),
                ingredientList,
                stepList
        );
    }


}
