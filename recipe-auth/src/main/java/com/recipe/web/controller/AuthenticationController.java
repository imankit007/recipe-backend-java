package com.recipe.web.controller;


import com.recipe.service.AuthService;
import com.recipe.web.model.LoginRequest;
import com.recipe.web.model.LoginResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.recipe.util.constants.AuthConstant.DEFAULT_EXPIRY;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthenticationController {

    private final AuthService authService;

    @PostMapping("login")
    private ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        String token = authService.login(request.username(), request.password());

        LoginResponse response = new LoginResponse(token,
                DEFAULT_EXPIRY);
        return ResponseEntity.ok(response);
    }





}
