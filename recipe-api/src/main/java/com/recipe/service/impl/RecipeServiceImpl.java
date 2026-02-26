package com.recipe.service.impl;


import com.recipe.converter.RecipeConverter;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.repository.RecipeRepository;
import com.recipe.dto.recipe.RecipeResponse;
import com.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl implements RecipeService {


    @Autowired
    private RecipeRepository recipeRepository;


    @Autowired
    private RecipeConverter recipeConverter;

    @Override
    public Page<RecipeResponse> listRecipes(PageRequest pageRequest) {
        Page<Recipe> page = recipeRepository.findAll(pageRequest);
        return page.map(recipeConverter::toRecipeResponse);
    }
}
