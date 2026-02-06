package com.recipe.converter;


import com.recipe.core.enums.Unit;
import com.recipe.core.utils.EnumMapper;
import com.recipe.core.utils.ProtoSafeUtils;
import com.recipe.data.jdbc.model.Ingredient;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.model.RecipeIngredient;
import com.recipe.data.jdbc.model.RecipeStep;
import com.recipe.data.jdbc.repository.IngredientRepository;
import com.recipe.grpc.api.recipe.v1.IngredientRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class RecipeConverter {

    private final EnumMapper enumMapper;

    private final IngredientRepository ingredientRepository;

    public Recipe toRecipeEntity(com.recipe.grpc.api.recipe.v1.CreateRecipeRequest request) {
        Recipe recipe = new Recipe();
        recipe.setTitle(request.getTitle());
        recipe.setDescription(request.getDescription());
        recipe.setDifficultyLevel(enumMapper.toDomain(request.getDifficulty()));
        recipe.setPrepTimeMinutes(request.getPrepTimeInMinutes());
        recipe.setCookTimeMinutes(request.getCookTimeInMinutes());
        recipe.setServings(request.getServings());
        recipe.setSteps(request.getStepsList().stream().map(it -> toRecipeStepEntity(it, recipe)).toList());
        recipe.setIngredients(toRecipeIngredientEntitySet(request.getIngredientsList(), recipe));
        return recipe;
    }

    public RecipeStep toRecipeStepEntity(com.recipe.grpc.api.recipe.v1.RecipeStep protoStep, Recipe recipe) {
        RecipeStep step = new RecipeStep();
        step.setStepNumber(protoStep.getStepNumber());
        step.setInstruction(protoStep.getInstruction());
        step.setDurationMinutes(protoStep.getDurationInMinutes());
        step.setMediaUrl(protoStep.getMediaUrl());
        step.setRecipe(recipe);
        return step;
    }

    public Set<RecipeIngredient> toRecipeIngredientEntitySet(List<IngredientRequest> protoRecipeIngredientList, Recipe recipe) {

        Set<Long> ingredientIds = protoRecipeIngredientList.stream()
                .map(com.recipe.grpc.api.recipe.v1.IngredientRequest::getIngredientId)
                .collect(Collectors.toSet());

        Map<Long, Ingredient> ingredientMap =
                ingredientRepository.findAllById(ingredientIds)
                        .stream()
                        .collect(Collectors.toMap(Ingredient::getId, Function.identity()));


        return protoRecipeIngredientList.stream()
                .map(ri -> toRecipeIngredientEntity(ri, recipe, ingredientMap))
                .collect(Collectors.toSet());
    }

    public RecipeIngredient toRecipeIngredientEntity(IngredientRequest protoRecipeIngredient, Recipe recipe, Map<Long, Ingredient> ingredientMap) {

        Ingredient ingredient = ingredientMap.get(protoRecipeIngredient.getIngredientId());
        if (ingredient == null) {
            throw new IllegalArgumentException("Ingredient with ID " + protoRecipeIngredient.getIngredientId() + " not found.");
        }
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setQuantity(BigDecimal.valueOf(protoRecipeIngredient.getQuantity()));
        recipeIngredient.setUnit(Unit.valueOf(protoRecipeIngredient.getUnit()));
        recipeIngredient.setIngredient(ingredientMap.get(protoRecipeIngredient.getIngredientId()));
        return recipeIngredient;
    }

    public com.recipe.grpc.api.recipe.v1.Recipe toRecipeProto(Recipe recipe) {
        return com.recipe.grpc.api.recipe.v1.Recipe.newBuilder()
                .setId(recipe.getId())
                .setTitle(recipe.getTitle())
                .setDescription(ProtoSafeUtils.safe(recipe.getDescription()))
                .setDifficulty(enumMapper.toProto(recipe.getDifficultyLevel()))
                .setPrepTimeInMinutes(ProtoSafeUtils.safe(recipe.getPrepTimeMinutes()))
                .setCookTimeInMinutes(ProtoSafeUtils.safe(recipe.getCookTimeMinutes()))
                .setServings(ProtoSafeUtils.safe(recipe.getServings()))
                .addAllSteps(toRecipeStepProtoList(recipe.getSteps()))
                .addAllIngredients(toRecipeIngredientProtoList(recipe.getIngredients()))
                .build();
    }

    private List<com.recipe.grpc.api.recipe.v1.RecipeIngredient> toRecipeIngredientProtoList(Set<RecipeIngredient> recipeIngredientList) {
        return recipeIngredientList.stream()
                .map(this::toRecipeIngredientProto)
                .collect(Collectors.toList());
    }

    private com.recipe.grpc.api.recipe.v1.RecipeIngredient toRecipeIngredientProto(RecipeIngredient ri) {
        return com.recipe.grpc.api.recipe.v1.RecipeIngredient.newBuilder()
                .setId(ri.getIngredient().getId())
                .setName(ri.getIngredient().getName())
                .setQuantity(ri.getQuantity().doubleValue())
                .setUnit(ri.getUnit().name())
                .build();
    }

    private List<com.recipe.grpc.api.recipe.v1.RecipeStep> toRecipeStepProtoList(List<RecipeStep> recipeStepList) {
        return recipeStepList.stream()
                .map(this::toRecipeStepProto)
                .collect(Collectors.toList());
    }

    private com.recipe.grpc.api.recipe.v1.RecipeStep toRecipeStepProto(RecipeStep step) {
        return com.recipe.grpc.api.recipe.v1.RecipeStep.newBuilder()
                .setStepNumber(step.getStepNumber())
                .setInstruction(step.getInstruction())
                .setDurationInMinutes(ProtoSafeUtils.safe(step.getDurationMinutes()))
                .setMediaUrl(ProtoSafeUtils.safe(step.getMediaUrl()))
                .build();
    }

    public void updateRecipeEntity(Recipe existingRecipe, com.recipe.grpc.api.recipe.v1.UpdateRecipeRequest request) {
        existingRecipe.setTitle(request.getTitle());
        existingRecipe.setDescription(request.getDescription());
        existingRecipe.setDifficultyLevel(enumMapper.toDomain(request.getDifficulty()));
        existingRecipe.setPrepTimeMinutes(request.getPrepTimeInMinutes());
        existingRecipe.setCookTimeMinutes(request.getCookTimeInMinutes());
        existingRecipe.setServings(request.getServings());
        existingRecipe.setSteps(request.getStepsList().stream().map(it -> toRecipeStepEntity(it, existingRecipe)).toList());
        existingRecipe.setIngredients(toRecipeIngredientEntitySet(request.getIngredientsList(), existingRecipe));
    }


}
