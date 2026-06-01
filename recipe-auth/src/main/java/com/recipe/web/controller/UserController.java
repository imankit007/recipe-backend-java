package com.recipe.web.controller;


import com.recipe.core.security.AuthContextHolder;
import com.recipe.data.auth.model.User;
import com.recipe.data.auth.repository.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for user-related operations.
 * Requires JWT authentication via Bearer token.
 */
@Slf4j
@RestController
@RequestMapping("/user")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    /**
     * Returns the current authenticated user's information.
     * User info is extracted from the JWT token in the Authorization header.
     *
     * @return ResponseEntity containing the authenticated user's information
     */
    @GetMapping("/me")
    public ResponseEntity<User> getMe() {
        Long userId = AuthContextHolder.getUserId();


        log.info("Context = {}", AuthContextHolder.getContext());


        log.info("Fetching current user info for userId: {}", userId);


        if (userId == null) {
            return ResponseEntity.notFound().build();
        }

        var user = userRepository.findById(userId);
        return user.map(ResponseEntity::ok)
                  .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
