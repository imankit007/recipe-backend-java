package com.recipe.converter;


import com.recipe.data.jdbc.model.Ingredient;
import com.recipe.dto.ingredient.IngredientReq;
import com.recipe.dto.ingredient.IngredientResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class IngredientConverter {

    public IngredientResponse toIngredientResponse(Ingredient ingredient) {
        return new IngredientResponse(ingredient.getId(), ingredient.getName());
    }



}
