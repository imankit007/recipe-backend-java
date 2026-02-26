//package com.recipe.controller;
//
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.security.SecurityRequirements;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@SecurityRequirements()
//@Tag(name = "Authentication", description = "Endpoints for user authentication")
//public class AuthController {
//
//    private final AuthGrpcClient authGrpcClient;
//
//    private final AuthConverter authConverter;
//
//    private AuthServiceGrpc.AuthServiceBlockingStub getClient(){return authGrpcClient.getAuthService();}
//
//    @Operation(
//        summary = "Login user",
//        description = "Authenticate user and return JWT token"
//    )
//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(
//            @Valid @RequestBody LoginRequest loginRequest
//    ){
//        com.recipe.grpc.api.auth.v1.LoginRequest request = authConverter.toProtoLoginRequest(loginRequest);
//        com.recipe.grpc.api.auth.v1.LoginResponse response = getClient().login(request);
//        return ResponseEntity.ok(authConverter.toLoginResponse(response));
//
//
//    }
//
//
//
//
//}
