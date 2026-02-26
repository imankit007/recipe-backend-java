package com.recipe.service.impl;


import com.recipe.converter.RecipeConverter;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.repository.RecipeRepository;
import com.recipe.dto.recipe.RecipeRequest;
import com.recipe.dto.recipe.RecipeResponse;
import com.recipe.service.RecipeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeConverter recipeConverter;

    @Override
    public Page<RecipeResponse> listRecipes(PageRequest pageRequest) {
        Page<Recipe> page = recipeRepository.findAll(pageRequest);
        return page.map(recipeConverter::toRecipeResponse);
    }

    @Override
    public RecipeResponse getRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() ->
                new EntityNotFoundException("Recipe not found")
        );
        return recipeConverter.toRecipeResponse(recipe);
    }

    @Override
    public RecipeResponse createRecipe(RecipeRequest recipeRequest) {
        Recipe recipe = recipeConverter.toRecipeEntity(recipeRequest);
        recipeRepository.save(recipe);
        return recipeConverter.toRecipeResponse(recipe);
    }

    @Override
    public RecipeResponse updateRecipe(Long recipeId, RecipeRequest recipeRequest) {
        Recipe recipe = recipeConverter.toRecipeEntity(recipeRequest);
        recipe.setId(recipeId);
        recipeRepository.save(recipe);
        return recipeConverter.toRecipeResponse(recipe);
    }

    @Override
    public void deleteRecipe(Long recipeId) {
        if (!recipeRepository.existsById(recipeId)) {
            throw new EntityNotFoundException("Recipe not found");
        }
        recipeRepository.deleteById(recipeId);
    }
}
