package com.recipe.grpc.adapter;

import com.recipe.grpc.api.recipe.v1.GetRecipeRequest;
import com.recipe.grpc.api.recipe.v1.GetRecipeResponse;
import com.recipe.grpc.api.recipe.v1.ListRecipesRequest;
import com.recipe.grpc.api.recipe.v1.ListRecipesResponse;

public interface RecipeServiceGrpcAdapter {

    GetRecipeResponse getRecipe(GetRecipeRequest request);

    ListRecipesResponse listRecipes(ListRecipesRequest request);

}
