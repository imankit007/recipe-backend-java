package com.recipe.api.gateway.controller;


import com.recipe.api.gateway.converter.RecipeConverter;
import com.recipe.api.gateway.dto.common.PagedResponse;
import com.recipe.api.gateway.dto.reipe.RecipeRequest;
import com.recipe.api.gateway.dto.reipe.RecipeResponse;
import com.recipe.api.gateway.grpc.client.RecipeGrpcClient;
import com.recipe.core.utils.EnumMapper;
import com.recipe.grpc.api.recipe.v1.*;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
                        .map(recipeConverter::toRecipeResponse)
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
        return recipeConverter.toRecipeResponse(response.getRecipe());
    }

    @PostMapping
    public RecipeResponse createRecipe(
            @RequestBody RecipeRequest recipeRequest
    ) {
        CreateRecipeRequest request = recipeConverter.toRecipeRequest(recipeRequest);
        CreateRecipeResponse response = getClient().createRecipe(request);
        return recipeConverter.toRecipeResponse(response.getRecipe());
    }

    @PutMapping("/{id}")
    public RecipeResponse updateRecipe(
            @RequestParam("id") Long id,
            @RequestBody RecipeRequest recipeRequest
    ) {
        UpdateRecipeRequest request = recipeConverter.toUpdateRecipeRequest(id, recipeRequest);
        UpdateRecipeResponse response = getClient().updateRecipe(request);
        return recipeConverter.toRecipeResponse(response.getRecipe());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe( @Valid
            @Parameter(description = "Id of recipe to be deleted", name = "id") @RequestParam("id") @NotNull Long id
    ) {
        DeleteRecipeRequest request = DeleteRecipeRequest.newBuilder().setId(id).build();
        DeleteRecipeResponse response = getClient().deleteRecipe(request);
        return ResponseEntity.noContent().build();
    }


}
