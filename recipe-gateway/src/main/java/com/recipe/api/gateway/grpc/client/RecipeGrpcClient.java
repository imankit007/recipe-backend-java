package com.recipe.api.gateway.grpc.client;

import com.recipe.api.gateway.grpc.interceptor.AuthInterceptor;
import com.recipe.grpc.api.recipe.v1.IngredientServiceGrpc;
import com.recipe.grpc.api.recipe.v1.RecipeServiceGrpc;
import com.recipe.grpc.api.recipe.v1.ReviewServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeGrpcClient extends AbstractGrpcClient {

    private static final String GRPC_CHANNEL_NAME = "recipe-service";

    @GrpcClient(value = "recipe-service", interceptors = {AuthInterceptor.class})
    private RecipeServiceGrpc.RecipeServiceBlockingStub recipeStub;

    @GrpcClient(value = "recipe-service", interceptors = {AuthInterceptor.class})
    private IngredientServiceGrpc.IngredientServiceBlockingStub ingredientStub;

    @GrpcClient(value = GRPC_CHANNEL_NAME, interceptors = {AuthInterceptor.class})
    private ReviewServiceGrpc.ReviewServiceBlockingStub reviewStub;

    public RecipeServiceGrpc.RecipeServiceBlockingStub recipe() {
        return recipeStub;
    }

    public IngredientServiceGrpc.IngredientServiceBlockingStub ingredient() {
        return ingredientStub;
    }

    public ReviewServiceGrpc.ReviewServiceBlockingStub review() {
        return reviewStub;
    }


}
