package com.recipe.api.gateway.grpc.client;

import com.recipe.api.gateway.grpc.channel.ChannelRegistry;
import com.recipe.api.gateway.grpc.interceptor.AuthInterceptor;
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
    private final ChannelRegistry channelRegistry;

    private final AuthInterceptor authInterceptor;


    private final ConcurrentMap<String, RecipeServiceGrpc.RecipeServiceBlockingStub> recipeServices = new ConcurrentHashMap<>();


    public RecipeServiceGrpc.RecipeServiceBlockingStub getRecipeService() {
        String channelName = "recipe-service";
        return recipeServices.computeIfAbsent(channelName, name -> {
            Channel channel = channelRegistry.getChannel(name);
            RecipeServiceGrpc.RecipeServiceBlockingStub stub = RecipeServiceGrpc.newBlockingStub(channel).withInterceptors(authInterceptor);
            return initialize(stub);
        });
    }


}
