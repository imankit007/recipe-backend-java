package com.recipe.grpc.adapter.impl;


import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.repository.RecipeRepository;
import com.recipe.grpc.adapter.RecipeServiceGrpcAdapter;
import com.recipe.grpc.api.recipe.v1.GetRecipeRequest;
import com.recipe.grpc.api.recipe.v1.GetRecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RecipeServiceGrpcAdapterImpl implements RecipeServiceGrpcAdapter {


    @Autowired
    private RecipeRepository recipeRepository;


    public GetRecipeResponse getRecipe(GetRecipeRequest getRecipeRequest) {

        var recipeId = getRecipeRequest.getId();

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        GetRecipeResponse.Builder responseBuilder = GetRecipeResponse.newBuilder();

        recipe.ifPresent(recipe1 -> {
            responseBuilder.setRecipe(
                    com.recipe.grpc.api.recipe.v1.Recipe.newBuilder().setId(recipe1.getId())
                            .setTitle(recipe1.getTitle())
                            .build()
            );
        });

        return responseBuilder.build();
    }


}
