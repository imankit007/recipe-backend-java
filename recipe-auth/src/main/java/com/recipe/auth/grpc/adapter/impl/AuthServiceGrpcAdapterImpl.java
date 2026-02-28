package com.recipe.auth.grpc.adapter.impl;

import com.recipe.auth.grpc.adapter.AuthServiceGrpcAdapter;
import com.recipe.auth.service.AuthService;
import com.recipe.grpc.api.auth.v1.LoginRequest;
import com.recipe.grpc.api.auth.v1.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceGrpcAdapterImpl implements AuthServiceGrpcAdapter {

    public static final Long DEFAULT_EXPIRY = 60L * 60 * 1000;

    private final AuthService authService;

    @Override
    public LoginResponse login(LoginRequest request) {

        String token = authService.login(request.getUsername(), request.getPassword());

        return LoginResponse.newBuilder().setToken(token)
                .setExpiresIn(DEFAULT_EXPIRY)
                .build();

    }
    
}
