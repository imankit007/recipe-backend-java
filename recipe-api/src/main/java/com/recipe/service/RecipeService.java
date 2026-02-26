package com.recipe.service;

import com.recipe.dto.recipe.RecipeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface RecipeService {

    Page<RecipeResponse> listRecipes(PageRequest pageRequest);



}
