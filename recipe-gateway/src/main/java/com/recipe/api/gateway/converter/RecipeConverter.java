package com.recipe.api.gateway.converter;


import com.recipe.api.gateway.dto.reipe.IngredientRequest;
import com.recipe.api.gateway.dto.reipe.RecipeRequest;
import com.recipe.core.utils.EnumMapper;
import com.recipe.grpc.api.recipe.v1.CreateRecipeRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecipeConverter {

    private final EnumMapper enumMapper;


    public CreateRecipeRequest toCreateRecipeRequest(RecipeRequest request) {

        CreateRecipeRequest.Builder builder = CreateRecipeRequest.newBuilder();

        if(request.title() != null){
            builder.setTitle(request.title());
        }
        if(request.description() != null){
            builder.setDescription(request.description());
        }
        if(request.prepTimeMinutes() != null){
            builder.setPrepTimeInMinutes(request.prepTimeMinutes());
        }
        if(request.cookTimeMinutes() != null){
            builder.setCookTimeInMinutes(request.cookTimeMinutes());
        }
        if(request.servings() != null){
            builder.setServings(request.servings());
        }
        if(request.difficulty() != null){
            builder.setDifficulty(enumMapper.toProto(request.difficulty()));
        }
        for (IngredientRequest ingredient : request.ingredients()) {
            builder.addIngredients(toProtoIngredientRequest(ingredient));
        }
        for (com.recipe.api.gateway.dto.reipe.RecipeStep step : request.steps()) {
            builder.addSteps(toProtoRecipeStep(step));
        }
        return builder.build();

    }


    public com.recipe.grpc.api.recipe.v1.IngredientRequest toProtoIngredientRequest(IngredientRequest ingredient) {

        com.recipe.grpc.api.recipe.v1.IngredientRequest.Builder builder = com.recipe.grpc.api.recipe.v1.IngredientRequest.newBuilder();

        if (ingredient.ingredientId() != null) {
            builder.setIngredientId(ingredient.ingredientId());
        }
        if (ingredient.name() != null) {
            builder.setName(ingredient.name());
        }
        if (ingredient.quantity() != null) {
            builder.setQuantity(ingredient.quantity().floatValue());
        }
        if (ingredient.unit() != null) {
            builder.setUnit(ingredient.unit());
        }

        return builder.build();
    }

    public com.recipe.grpc.api.recipe.v1.RecipeStep toProtoRecipeStep(com.recipe.api.gateway.dto.reipe.RecipeStep step) {

        com.recipe.grpc.api.recipe.v1.RecipeStep.Builder builder = com.recipe.grpc.api.recipe.v1.RecipeStep.newBuilder();

        if (step.stepNumber() != null) {
            builder.setStepNumber(step.stepNumber());
        }
        if (step.instruction() != null) {
            builder.setInstruction(step.instruction());
        }
        if (step.durationMinutes() != null) {
            builder.setDurationInMinutes(step.durationMinutes());
        }
        if (step.mediaUrl() != null) {
            builder.setMediaUrl(step.mediaUrl());
        }
        return builder.build();
    }


}
