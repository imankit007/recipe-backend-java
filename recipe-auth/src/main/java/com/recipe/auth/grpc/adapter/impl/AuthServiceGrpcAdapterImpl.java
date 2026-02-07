package com.recipe.auth.grpc.adapter.impl;


import com.recipe.grpc.api.auth.v1.LoginRequest;
import com.recipe.grpc.api.auth.v1.LoginResponse;
import com.recipe.auth.grpc.adapter.AuthServiceGrpcAdapter;
import com.recipe.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceGrpcAdapterImpl implements AuthServiceGrpcAdapter {

    private final AuthService authService;

    @Override
    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("Username and password must not be null");
        }

        String token = authService.login(username, password);

        return LoginResponse.newBuilder()
                .setToken(token)
                .setExpiresIn(10 * 60 * 60 * 10) // Token valid for 10 hours
                .build();
    }

}
