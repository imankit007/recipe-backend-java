package com.recipe.api.gateway.config;

import com.recipe.core.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtClaimsParser {

    private final JwtUtil jwtUtil;

    public Claims getClaimsFromToken(String token) {
        return jwtUtil.getClaimsFromToken(token);
    }

}
