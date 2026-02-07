package com.recipe.auth.service;


import com.recipe.data.auth.repository.UserRepository;
import com.recipe.core.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {


    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    public String login(String username, String password) {

        var userOpt = userRepository.findByEmail(username);

        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(password)) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtUtil.generateToken(username);
    }




}
