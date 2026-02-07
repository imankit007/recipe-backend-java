package com.recipe.api.gateway.controller;


import com.recipe.api.gateway.dto.ingredient.IngredientReq;
import com.recipe.api.gateway.dto.ingredient.IngredientResponse;
import com.recipe.api.gateway.grpc.client.RecipeGrpcClient;
import com.recipe.grpc.api.recipe.v1.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
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

    private final RecipeGrpcClient grpcClient;

    public IngredientServiceGrpc.IngredientServiceBlockingStub getClient() {
        return grpcClient.getIngredientService();
    }


    @PostMapping
    @Operation(
        summary = "Create ingredient",
        description = "Create a new ingredient"
    )
    public ResponseEntity<IngredientResponse> createIngredient(
            @RequestBody IngredientReq ingredientRequest
    ) {
        CreateIngredientRequest request = CreateIngredientRequest.newBuilder()
                .setName(ingredientRequest.name())
                .build();

        CreateIngredientResponse response = getClient().createIngredient(request);

        return ResponseEntity.ok(new IngredientResponse(response.getId(), response.getName()));

    }



}
