package com.recipe.auth.grpc;


import com.recipe.grpc.api.auth.v1.AuthServiceGrpc;
import com.recipe.grpc.api.auth.v1.LoginRequest;
import com.recipe.grpc.api.auth.v1.LoginResponse;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class AuthServiceGrpcImpl extends AuthServiceGrpc.AuthServiceImplBase {




    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {

    }
}
