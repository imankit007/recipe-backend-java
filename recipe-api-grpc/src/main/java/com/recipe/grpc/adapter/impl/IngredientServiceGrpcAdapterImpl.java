package com.recipe.grpc.adapter.impl;

import com.recipe.data.jdbc.model.Ingredient;
import com.recipe.data.jdbc.repository.IngredientRepository;
import com.recipe.grpc.adapter.IngredientServiceGrpcAdapter;
import com.recipe.grpc.api.recipe.v1.CreateIngredientRequest;
import com.recipe.grpc.api.recipe.v1.CreateIngredientResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IngredientServiceGrpcAdapterImpl implements IngredientServiceGrpcAdapter {

    private final IngredientRepository ingredientRepository;

    @Override
    public CreateIngredientResponse createIngredient(CreateIngredientRequest request) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(request.getName());

        ingredient = ingredientRepository.save(ingredient);

        return CreateIngredientResponse.newBuilder()
                .setId(ingredient.getId())
                .setName(ingredient.getName())
                .build();

    }
}
