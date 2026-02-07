package com.recipeauth.grpc.adapter.impl;


import com.recipe.grpc.api.auth.v1.LoginResponse;
import com.recipeauth.grpc.adapter.AuthServiceGrpcAdapter;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceGrpcAdapterImpl implements AuthServiceGrpcAdapter {

    @Override
    public LoginResponse login(com.recipe.grpc.api.auth.v1.LoginRequest request) {
        return LoginResponse.newBuilder().build();
    }

}
