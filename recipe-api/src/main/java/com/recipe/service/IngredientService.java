package com.recipe.service;


import com.recipe.dto.ingredient.IngredientReq;
import com.recipe.dto.ingredient.IngredientResponse;

public interface IngredientService {


    IngredientResponse createIngredient(IngredientReq ingredientReq);


}
