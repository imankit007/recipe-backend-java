package com.recipe.auth.grpc.impl;


import com.recipe.grpc.api.auth.v1.AuthServiceGrpc;
import com.recipe.grpc.api.auth.v1.LoginRequest;
import com.recipe.grpc.api.auth.v1.LoginResponse;
import com.recipe.auth.grpc.adapter.AuthServiceGrpcAdapter;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class AuthServiceGrpcImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final AuthServiceGrpcAdapter authServiceGrpcAdapter;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        responseObserver.onNext(authServiceGrpcAdapter.login(request));
        responseObserver.onCompleted();
    }
}
