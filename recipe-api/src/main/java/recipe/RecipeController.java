package recipe.controller;

import recipe.dto.common.PagedResponse;
import recipe.dto.recipe.RecipeRequest;
import recipe.dto.recipe.RecipeResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/recipes")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class RecipeController {

//    private final RecipeGrpcClient grpcClient;
//
//    private final RecipeConverter recipeConverter;

    @GetMapping("/")
    public PagedResponse<RecipeResponse> getRecipes(
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size
    ) {

        return new PagedResponse<>(
                Collections.emptyList(),
                0, 0, 0, 0
        );
    }

    @GetMapping("/{id}")
    public RecipeResponse getRecipeById(
            @RequestParam("id") Long id
    ) {
        return RecipeResponse.EMPTY;
    }

    @PostMapping
    public RecipeResponse createRecipe(
            @RequestBody RecipeRequest recipeRequest
    ) {

        return RecipeResponse.EMPTY;
    }

    @PutMapping("/{id}")
    public RecipeResponse updateRecipe(
            @RequestParam("id") Long id,
            @RequestBody RecipeRequest recipeRequest
    ) {
       return RecipeResponse.EMPTY;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe( @Valid
            @Parameter(description = "Id of recipe to be deleted", name = "id") @RequestParam("id") @NotNull Long id
    ) {
        return ResponseEntity.noContent().build();
    }


}
