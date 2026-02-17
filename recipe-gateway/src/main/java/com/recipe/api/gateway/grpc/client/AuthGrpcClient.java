package com.recipe.api.gateway.grpc.client;

import com.recipe.grpc.api.auth.v1.AuthServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGrpcClient  extends AbstractGrpcClient{

    @GrpcClient("recipe-auth-service")
    private AuthServiceGrpc.AuthServiceBlockingStub authServices;

    public AuthServiceGrpc.AuthServiceBlockingStub getAuthService(){
      return authServices;
    }





}
