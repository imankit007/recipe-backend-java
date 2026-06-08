package com.recipe.util;


import com.recipe.data.auth.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
@AllArgsConstructor
public class JwtTokenProvider {

    private final KeyPair keyPair;

    /**
     * Generates a JWT token with user information as claims.
     *
     * @param user the authenticated user
     * @return JWT token string
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("userId", user.getId())
                .claim("email", user.getEmail())
                .claim("displayName", user.getDisplayName())
                .claim("avatarUrl", user.getAvatarUrl())
                .claim("role", user.getRole().toString())
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

    /**
     * Generates a JWT token with just username (for backward compatibility).
     *
     * @param username the username/email
     * @return JWT token string
     */
    public String generateTokenByUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

}
