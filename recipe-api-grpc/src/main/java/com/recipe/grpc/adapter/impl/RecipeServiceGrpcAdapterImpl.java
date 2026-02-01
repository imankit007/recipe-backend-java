package com.recipe.grpc.adapter.impl;


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

import java.util.EnumMap;
import java.util.Optional;

@Service
public class RecipeServiceGrpcAdapterImpl implements RecipeServiceGrpcAdapter {


    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private EnumMapper enumMapper;


    public GetRecipeResponse getRecipe(GetRecipeRequest getRecipeRequest) {

        var recipeId = getRecipeRequest.getId();

        Optional<Recipe> recipe = recipeRepository.findById(recipeId);
        GetRecipeResponse.Builder responseBuilder = GetRecipeResponse.newBuilder();

        recipe.ifPresent(recipe1 -> {
            responseBuilder.setRecipe(
                    com.recipe.grpc.api.recipe.v1.Recipe.newBuilder().setId(recipe1.getId())
                            .setTitle(recipe1.getTitle())
                            .setDifficulty(enumMapper.toProto(recipe1.getDifficultyLevel()))
                            .build()
            );
        });

        return responseBuilder.build();
    }

    @Override
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
    public CreateRecipeResponse createRecipe(CreateRecipeRequest request) {
        return CreateRecipeResponse.newBuilder()
                .setRecipe(

                        com.recipe.grpc.api.recipe.v1.Recipe.newBuilder()
                                .setId(1L)
                                .setTitle(request.getTitle())
                                .setDifficulty(request.getDifficulty())
                                .build()
                )
                .build();
    }

    @Override
    public UpdateRecipeResponse updateRecipe(UpdateRecipeRequest request) {
        return UpdateRecipeResponse.newBuilder().build();
    }

    @Override
    public DeleteRecipeResponse deleteRecipe(DeleteRecipeRequest request) {
        return DeleteRecipeResponse.newBuilder().build();
    }
}
