package com.recipe.api.gateway.converter;


import com.recipe.api.gateway.dto.auth.LoginRequest;
import com.recipe.api.gateway.dto.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthConverter {

    public com.recipe.grpc.api.auth.v1.LoginRequest toProtoLoginRequest(LoginRequest request) {
        com.recipe.grpc.api.auth.v1.LoginRequest.Builder builder = com.recipe.grpc.api.auth.v1.LoginRequest.newBuilder();

        if (request.username() != null) {
            builder.setUsername(request.username());
        }
        if (request.password() != null) {
            builder.setPassword(request.password());
        }
        return builder.build();
    }

     public LoginResponse toLoginResponse(com.recipe.grpc.api.auth.v1.LoginResponse response) {
         return new LoginResponse(
                 response.getToken(),
                 response.getExpiresIn()
         );
     }




}
