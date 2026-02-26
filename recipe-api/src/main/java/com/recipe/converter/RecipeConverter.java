package com.recipe.converter;

import com.recipe.core.enums.Unit;
import com.recipe.data.jdbc.model.Ingredient;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.repository.IngredientRepository;
import com.recipe.dto.recipe.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecipeConverter {

    private final IngredientRepository ingredientRepository;

    public RecipeResponse toRecipeResponse(Recipe recipe) {
        Objects.requireNonNull(recipe, "recipe must not be null");
        List<RecipeIngredient> ingredientList = Optional.ofNullable(recipe.getIngredients())
                .orElseGet(Set::of)
                .stream()
                .map(this::toRecipeIngredientDto)
                .toList();
        List<RecipeStep> stepList = Optional.ofNullable(recipe.getSteps())
                .orElseGet(List::of)
                .stream()
                .map(this::toRecipeStepDto)
                .toList();

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

    private RecipeIngredient toRecipeIngredientDto(com.recipe.data.jdbc.model.RecipeIngredient recipeIngredient) {
        return new RecipeIngredient(
                recipeIngredient.getId(),
                recipeIngredient.getIngredient().getName(),
                recipeIngredient.getQuantity().doubleValue(),
                recipeIngredient.getUnit().toString()
        );
    }

    public RecipeStep toRecipeStepDto(com.recipe.data.jdbc.model.RecipeStep rs) {
        return new RecipeStep(
                rs.getStepNumber(),
                rs.getInstruction(),
                rs.getDurationMinutes(),
                rs.getMediaUrl()
        );
    }

    public Recipe toRecipeEntity(RecipeRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        Recipe recipe = new Recipe();
        recipe.setTitle(request.title());
        recipe.setDescription(request.description());
        recipe.setDifficultyLevel(request.difficulty());
        recipe.setPrepTimeMinutes(request.prepTimeMinutes());
        recipe.setCookTimeMinutes(request.cookTimeMinutes());
        recipe.setServings(request.servings());

        List<RecipeIngredientRequest> ingredientRequests =
                Optional.ofNullable(request.ingredients()).orElseGet(List::of);

        Map<Long, Ingredient> ingredientMap = loadIngredients(ingredientRequests);

        recipe.setIngredients(
                Optional.ofNullable(request.ingredients())
                        .orElseGet(List::of)
                        .stream()
                        .map(i -> toRecipeIngredientEntity(i, recipe, ingredientMap))
                        .collect(Collectors.toSet())
        );

        recipe.setSteps(
                Optional.ofNullable(request.steps())
                        .orElseGet(List::of)
                        .stream()
                        .map(s -> toRecipeStepEntity(s, recipe))
                        .toList()
        );

        return recipe;
    }

    private Map<Long, Ingredient> loadIngredients(List<RecipeIngredientRequest> requests) {
        Set<Long> ids = requests.stream()
                .map(RecipeIngredientRequest::ingredientId)
                .collect(Collectors.toSet());

        return ingredientRepository.findAllById(ids).stream()
                .collect(Collectors.toMap(Ingredient::getId, Function.identity()));
    }


    private com.recipe.data.jdbc.model.RecipeIngredient toRecipeIngredientEntity(
            RecipeIngredientRequest recipeIngredientRequest,
            Recipe recipe,
            Map<Long, Ingredient> ingredientMap
    ) {
        Ingredient ingredient = ingredientMap.get(recipeIngredientRequest.ingredientId());
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient not found: " + recipeIngredientRequest.ingredientId());
        }
        com.recipe.data.jdbc.model.RecipeIngredient recipeIngredient = new com.recipe.data.jdbc.model.RecipeIngredient();
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setQuantity(recipeIngredientRequest.quantity());
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setUnit(Unit.valueOf(recipeIngredientRequest.unit().toUpperCase(Locale.ROOT)));
        return recipeIngredient;
    }

    private com.recipe.data.jdbc.model.RecipeStep toRecipeStepEntity(RecipeStep recipeStep, Recipe recipe) {
        com.recipe.data.jdbc.model.RecipeStep step = new com.recipe.data.jdbc.model.RecipeStep();
        step.setStepNumber(recipeStep.stepNumber());
        step.setInstruction(recipeStep.instruction());
        step.setDurationMinutes(recipeStep.durationMinutes());
        step.setMediaUrl(recipeStep.mediaUrl());
        step.setRecipe(recipe);
        return step;
    }


}
