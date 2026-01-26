package com.recipe.api.gateway.controller;


import com.recipe.api.gateway.grpc.client.RecipeGrpcClient;
import com.recipe.core.enums.RecipeDifficultyLevel;
import com.recipe.core.utils.EnumMapper;
import com.recipe.grpc.api.recipe.v1.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private EnumMapper enumMapper;

    @Autowired
    private RecipeGrpcClient grpcClient;

    public RecipeServiceGrpc.RecipeServiceBlockingStub getClient() {
        return grpcClient.getRecipeService();
    }

    @GetMapping("/")
    public PagedResponse<RecipeDto> getRecipes(
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
                        .map(r -> new RecipeDto(r.getId(), r.getTitle(), enumMapper.toDomain(r.getDifficulty())))
                        .toList(),
                Math.max(response.getPage().getPage() + 1, 1),
                response.getPage().getSize(),
                response.getPage().getTotalElements(),
                response.getPage().getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public RecipeDto getRecipeById(
            @RequestParam("id") Long id
    ) {
        GetRecipeRequest request = GetRecipeRequest.newBuilder().setId(id).build();
        GetRecipeResponse response = getClient().getRecipe(request);
        return new RecipeDto(response.getRecipe().getId(), response.getRecipe().getTitle(),
                enumMapper.toDomain(response.getRecipe().getDifficulty()));
    }

    public record PagedResponse<T>(
            List<T> content,
            int page,
            int size,
            long totalElements,
            int totalPages
    ) {
    }

    public record RecipeDto(Long id, String name, RecipeDifficultyLevel difficulty) {
    }
}
