package com.recipe.controller;


import com.recipe.dto.auth.LoginRequest;
import com.recipe.dto.auth.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@SecurityRequirements()
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {


    @Operation(
        summary = "Login user",
        description = "Authenticate user and return JWT token"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest
    ){

        return ResponseEntity.noContent().build();


    }




}
