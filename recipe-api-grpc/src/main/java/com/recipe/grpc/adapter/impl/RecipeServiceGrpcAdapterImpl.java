package com.recipe.grpc.adapter.impl;


import com.recipe.converter.RecipeConverter;
import com.recipe.core.utils.EnumMapper;
import com.recipe.core.utils.PageUtils;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.repository.RecipeRepository;
import com.recipe.grpc.adapter.RecipeServiceGrpcAdapter;
import com.recipe.grpc.api.recipe.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.Optional;

@Service
public class RecipeServiceGrpcAdapterImpl implements RecipeServiceGrpcAdapter {


    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private EnumMapper enumMapper;

    @Autowired
    private RecipeConverter recipeConverter;


    @Override
    @Transactional
    public GetRecipeResponse getRecipe(GetRecipeRequest getRecipeRequest) {

        var recipeId = getRecipeRequest.getId();

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        GetRecipeResponse.Builder responseBuilder = GetRecipeResponse.newBuilder();

        recipe.ifPresent(it -> {
            responseBuilder.setRecipe(
                  recipeConverter.toRecipeProto(it)
            );
        });

        return responseBuilder.build();
    }

    @Override
    @Transactional
    public ListRecipesResponse listRecipes(ListRecipesRequest request) {
        PageRequest pageable = org.springframework.data.domain.PageRequest.of(request.getPage(), request.getSize());

        Page<Recipe> recipePage = recipeRepository.findAll(pageable);
        ListRecipesResponse.Builder responseBuilder = ListRecipesResponse.newBuilder()
                .addAllRecipes(recipePage.getContent().stream().map(r ->
                        com.recipe.grpc.api.recipe.v1.Recipe.newBuilder()
                                .setId(r.getId())
                                .setTitle(r.getTitle())
                                .setDifficulty(enumMapper.toProto(r.getDifficultyLevel()))
                                .build()
                ).toList())
                .setPage(PageUtils.toGrpcPage(recipePage, request.getPage()));

        return responseBuilder.build();
    }

    @Override
    @Transactional
    public CreateRecipeResponse createRecipe(CreateRecipeRequest request) {
        Recipe recipe = recipeConverter.toRecipeEntity(request);
        recipe = recipeRepository.save(recipe);
        return CreateRecipeResponse.newBuilder()
                .setRecipe(recipeConverter.toRecipeProto(recipe))
                .build();
    }

    @Override
    @Transactional
    public UpdateRecipeResponse updateRecipe(UpdateRecipeRequest request) {
        return UpdateRecipeResponse.newBuilder().build();
    }

    @Override
    @Transactional
    public DeleteRecipeResponse deleteRecipe(DeleteRecipeRequest request) {
        return DeleteRecipeResponse.newBuilder().build();
    }
}
