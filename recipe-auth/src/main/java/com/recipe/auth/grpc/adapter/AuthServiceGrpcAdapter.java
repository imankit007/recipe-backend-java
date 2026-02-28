package com.recipe.auth.grpc.adapter;

import com.recipe.grpc.api.auth.v1.LoginRequest;
import com.recipe.grpc.api.auth.v1.LoginResponse;

public interface AuthServiceGrpcAdapter {

    LoginResponse login(LoginRequest request);

}
