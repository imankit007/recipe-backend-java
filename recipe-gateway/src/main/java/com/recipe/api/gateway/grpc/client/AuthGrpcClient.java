package com.recipe.api.gateway.grpc.client;

import com.recipe.api.gateway.grpc.channel.ChannelRegistry;
import com.recipe.grpc.api.auth.v1.AuthServiceGrpc;
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
public class AuthGrpcClient  extends AbstractGrpcClient{
    private final ChannelRegistry channelRegistry;

    private final ConcurrentMap<String, AuthServiceGrpc.AuthServiceBlockingStub> authServices = new ConcurrentHashMap<>();

    public AuthServiceGrpc.AuthServiceBlockingStub getAuthService(){
        String channelName = "recipe-auth-service";
        return authServices.computeIfAbsent(channelName, name -> {
            Channel channel = channelRegistry.getChannel(name);
            AuthServiceGrpc.AuthServiceBlockingStub stub = AuthServiceGrpc.newBlockingStub(channel);
            return initialize(stub);
        });
    }





}
