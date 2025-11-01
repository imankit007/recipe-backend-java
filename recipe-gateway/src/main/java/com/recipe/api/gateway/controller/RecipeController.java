package com.recipe.api.gateway.controller;


import com.recipe.api.gateway.grpc.client.RecipeGrpcClient;
import com.recipe.grpc.api.recipe.v1.ListRecipesRequest;
import com.recipe.grpc.api.recipe.v1.ListRecipesResponse;
import com.recipe.grpc.api.recipe.v1.Recipe;
import com.recipe.grpc.api.recipe.v1.RecipeServiceGrpc;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipeController {

    @Autowired
    private RecipeGrpcClient grpcClient;

    public  RecipeServiceGrpc.RecipeServiceBlockingStub getClient(){
        return grpcClient.getRecipeService();
    }

    @GetMapping("/list")
    public List<RecipeDto> getRecipes() {

        ListRecipesRequest request = ListRecipesRequest.newBuilder().build();

       ListRecipesResponse response =  getClient().listRecipes(request);

        return response.getRecipesList().stream().map(r -> new RecipeDto(r.getId(), r.getTitle()))
                .toList();

    }

    public record RecipeDto(String id, String name){}
}
