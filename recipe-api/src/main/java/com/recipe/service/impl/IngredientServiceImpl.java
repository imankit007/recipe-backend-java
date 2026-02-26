package com.recipe.service.impl;

import com.recipe.converter.IngredientConverter;
import com.recipe.data.jdbc.model.Ingredient;
import com.recipe.data.jdbc.repository.IngredientRepository;
import com.recipe.dto.ingredient.IngredientReq;
import com.recipe.dto.ingredient.IngredientResponse;
import com.recipe.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    private final IngredientConverter ingredientConverter;

    static public String usedInRecipeMessageTemplate ;

    public IngredientResponse createIngredient(IngredientReq ingredientReq) {
        Ingredient ingredient = new Ingredient();
        ingredient.setName(ingredientReq.name());
        Ingredient savedIngredient = ingredientRepository.save(ingredient);
        return ingredientConverter.toIngredientResponse(savedIngredient);
    }
}
