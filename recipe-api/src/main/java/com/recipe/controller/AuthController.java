package com.recipe.controller;


import com.recipe.dto.auth.LoginRequest;
import com.recipe.dto.auth.LoginResponse;
import com.recipe.grpc.api.auth.v1.AuthServiceGrpc;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {


    @GrpcClient("auth-service")
    private AuthServiceGrpc.AuthServiceBlockingStub authServiceBlockingStub;


    @Operation(
            summary = "Login user",
            description = "Authenticate user and return JWT token"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ) {
        com.recipe.grpc.api.auth.v1.LoginRequest grpcRequest = com.recipe.grpc.api.auth.v1.LoginRequest.newBuilder()
                .setUsername(loginRequest.username())
                .setPassword(loginRequest.password())
                .build();

        com.recipe.grpc.api.auth.v1.LoginResponse grpcResponse = authServiceBlockingStub.login(grpcRequest);

        return new ResponseEntity<>(new LoginResponse(grpcResponse.getToken(), grpcResponse.getExpiresIn()), HttpStatus.OK);
    }


}
