package com.recipe.grpc.adapter;

import com.recipe.grpc.api.recipe.v1.GetRecipeRequest;
import com.recipe.grpc.api.recipe.v1.GetRecipeResponse;

public interface RecipeServiceGrpcAdapter {

    GetRecipeResponse getRecipe(GetRecipeRequest request);

}
