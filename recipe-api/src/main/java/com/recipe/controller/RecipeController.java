package com.recipe.controller;

import com.recipe.dto.common.PagedResponse;
import com.recipe.dto.recipe.RecipeRequest;
import com.recipe.dto.recipe.RecipeResponse;
import com.recipe.service.RecipeService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/recipes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public PagedResponse<RecipeResponse> getRecipes(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<RecipeResponse> responsePage = recipeService.listRecipes(pageRequest);

        return new PagedResponse<>(responsePage.getContent(),
                responsePage.getNumber(),
                responsePage.getNumberOfElements(),
                responsePage.getTotalElements(),
                responsePage.getTotalPages()
        );
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipeById(
            @PathVariable("id") Long id
    ) {
        return RecipeResponse.EMPTY;
    }

    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(
            @RequestBody RecipeRequest recipeRequest
    ) {
        return new ResponseEntity<>(recipeService.createRecipe(recipeRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public RecipeResponse updateRecipe(
            @PathVariable("id") Long id,
            @RequestBody RecipeRequest recipeRequest
    ) {
        return recipeService.updateRecipe(id, recipeRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            @Valid
            @Parameter(description = "Id of recipe to be deleted", name = "id")
            @PathVariable("id")
            @NotNull Long id
    ) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }


}
