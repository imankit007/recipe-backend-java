package com.recipe.auth.grpc;


import com.recipe.auth.grpc.adapter.AuthServiceGrpcAdapter;
import com.recipe.grpc.api.auth.v1.AuthServiceGrpc;
import com.recipe.grpc.api.auth.v1.LoginRequest;
import com.recipe.grpc.api.auth.v1.LoginResponse;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class AuthServiceGrpcImpl extends AuthServiceGrpc.AuthServiceImplBase {

    private final AuthServiceGrpcAdapter  authServiceGrpcAdapter;

    @Override
    public void login(LoginRequest request, StreamObserver<LoginResponse> responseObserver) {
        responseObserver.onNext(authServiceGrpcAdapter.login(request));
        responseObserver.onCompleted();
    }
}
