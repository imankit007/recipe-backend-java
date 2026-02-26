package com.recipe.controller;


import com.recipe.dto.ingredient.IngredientReq;
import com.recipe.dto.ingredient.IngredientResponse;
import com.recipe.service.IngredientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    @Operation(
            summary = "Create ingredient",
            description = "Create a new ingredient"
    )
    public ResponseEntity<IngredientResponse> createIngredient(
            @RequestBody IngredientReq ingredientRequest
    ) {
        IngredientResponse response = ingredientService.createIngredient(ingredientRequest);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
