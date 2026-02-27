package com.recipe.auth.service;


import com.recipe.auth.util.JwtTokenProvider;
import com.recipe.data.auth.repository.UserRepository;
import com.recipe.core.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final JwtTokenProvider jwtUtil;

    public String login(String username, String password) {
        var userOpt = userRepository.findByEmail(username);

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        return jwtUtil.generateToken(username);
    }




}
