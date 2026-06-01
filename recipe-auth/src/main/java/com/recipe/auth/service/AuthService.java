package com.recipe.auth.service;


import com.recipe.auth.util.JwtTokenProvider;
import com.recipe.data.auth.repository.UserRepository;
import com.recipe.data.auth.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param username the user's email
     * @param password the user's password
     * @return JWT token string
     * @throws IllegalArgumentException if credentials are invalid
     */
    public String login(String username, String password) {
        var userOpt = userRepository.findByEmail(username);

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        User user = userOpt.get();
        return jwtTokenProvider.generateToken(user);
    }




}
