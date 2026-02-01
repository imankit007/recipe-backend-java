package com.recipe.api.gateway.controller;


import com.recipe.api.gateway.converter.RecipeConverter;
import com.recipe.api.gateway.dto.common.PagedResponse;
import com.recipe.api.gateway.dto.reipe.RecipeRequest;
import com.recipe.api.gateway.dto.reipe.RecipeResponse;
import com.recipe.api.gateway.grpc.client.RecipeGrpcClient;
import com.recipe.core.utils.EnumMapper;
import com.recipe.grpc.api.recipe.v1.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final EnumMapper enumMapper;

    private final RecipeGrpcClient grpcClient;

    private final RecipeConverter recipeConverter;

    public RecipeServiceGrpc.RecipeServiceBlockingStub getClient() {
        return grpcClient.getRecipeService();
    }

    @GetMapping("/")
    public PagedResponse<RecipeResponse> getRecipes(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        ListRecipesRequest request = ListRecipesRequest.newBuilder()
                .setPage(Math.max(0, page - 1))
                .setSize(Math.max(0, size))
                .build();

        ListRecipesResponse response = getClient().listRecipes(request);
        return new PagedResponse<>(
                response.getRecipesList().stream()
                        .map(r -> new RecipeResponse(r.getId(), r.getTitle(), enumMapper.toDomain(r.getDifficulty())))
                        .toList(),
                Math.max(response.getPage().getPage() + 1, 1),
                response.getPage().getSize(),
                response.getPage().getTotalElements(),
                response.getPage().getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipeById(
            @RequestParam("id") Long id
    ) {
        GetRecipeRequest request = GetRecipeRequest.newBuilder().setId(id).build();
        GetRecipeResponse response = getClient().getRecipe(request);
        return new RecipeResponse(response.getRecipe().getId(), response.getRecipe().getTitle(),
                enumMapper.toDomain(response.getRecipe().getDifficulty()));
    }

    @PostMapping
    public RecipeResponse createRecipe(
            @RequestBody RecipeRequest recipeRequest
    ){
        CreateRecipeRequest request = recipeConverter.toCreateRecipeRequest(recipeRequest);
        CreateRecipeResponse response = getClient().createRecipe(request);
        return new RecipeResponse(response.getRecipe().getId(), response.getRecipe().getTitle(),
                enumMapper.toDomain(response.getRecipe().getDifficulty()));

    }




}
