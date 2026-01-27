package com.recipe.grpc.adapter;

import com.recipe.grpc.api.recipe.v1.*;

public interface RecipeServiceGrpcAdapter {

    GetRecipeResponse getRecipe(GetRecipeRequest request);

    ListRecipesResponse listRecipes(ListRecipesRequest request);

    CreateRecipeResponse createRecipe(CreateRecipeRequest request);

    UpdateRecipeResponse updateRecipe(UpdateRecipeRequest request);

    DeleteRecipeResponse deleteRecipe(DeleteRecipeRequest request);

}
