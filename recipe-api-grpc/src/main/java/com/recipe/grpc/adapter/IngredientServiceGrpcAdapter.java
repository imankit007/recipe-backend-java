package com.recipe.grpc.adapter;

import com.recipe.grpc.api.recipe.v1.CreateIngredientRequest;
import com.recipe.grpc.api.recipe.v1.CreateIngredientResponse;

public interface IngredientServiceGrpcAdapter {

    CreateIngredientResponse createIngredient(CreateIngredientRequest request);

}
