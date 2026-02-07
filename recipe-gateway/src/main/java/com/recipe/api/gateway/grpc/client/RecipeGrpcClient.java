package com.recipe.api.gateway.grpc.client;

import com.recipe.api.gateway.grpc.channel.ChannelRegistry;
import com.recipe.api.gateway.grpc.interceptor.AuthInterceptor;
import com.recipe.grpc.api.recipe.v1.IngredientServiceGrpc;
import com.recipe.grpc.api.recipe.v1.RecipeServiceGrpc;
import io.grpc.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Slf4j
@Component
@RequiredArgsConstructor
public class RecipeGrpcClient extends AbstractGrpcClient {
    public static final String RECIPE_SERVICE = "recipe-service";
    private final ChannelRegistry channelRegistry;

    private final AuthInterceptor authInterceptor;


    private final ConcurrentMap<String, RecipeServiceGrpc.RecipeServiceBlockingStub> recipeServices = new ConcurrentHashMap<>();

    private final ConcurrentMap<String, IngredientServiceGrpc.IngredientServiceBlockingStub> ingredientServices = new ConcurrentHashMap<>();


    public RecipeServiceGrpc.RecipeServiceBlockingStub getRecipeService() {
        return recipeServices.computeIfAbsent(RECIPE_SERVICE, name -> {
            Channel channel = channelRegistry.getChannel(name);
            RecipeServiceGrpc.RecipeServiceBlockingStub stub = RecipeServiceGrpc.newBlockingStub(channel).withInterceptors(authInterceptor);
            return initialize(stub);
        });
    }

    public IngredientServiceGrpc.IngredientServiceBlockingStub getIngredientService() {
        return ingredientServices.computeIfAbsent(RECIPE_SERVICE, name -> {
            Channel channel = channelRegistry.getChannel(name);
            IngredientServiceGrpc.IngredientServiceBlockingStub stub = IngredientServiceGrpc.newBlockingStub(channel).withInterceptors(authInterceptor);
            return initialize(stub);
        });
    }


}
