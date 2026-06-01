package com.recipe.core.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

/**
 * Utility component for JWT token parsing and validation.
 * Provides methods to extract claims and user information from JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final KeyPair keyPair;

    /**
     * Parses JWT token and extracts all claims.
     *
     * @param token the JWT token string (should include "Bearer " prefix removed)
     * @return Claims object containing all token claims
     * @throws JwtException if token is invalid or expired
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (JwtException e) {
            throw new JwtException("Invalid JWT token: " + e.getMessage(), e);
        }
    }

    /**
     * Validates JWT token without extracting claims.
     *
     * @param token the JWT token string
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(keyPair.getPublic())
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extracts AuthContext from JWT token claims.
     *
     * @param token the JWT token string
     * @return AuthContext containing user information
     * @throws JwtException if token is invalid
     */
    public AuthContext extractAuthContext(String token) {
        Claims claims = parseToken(token);

        return AuthContext.builder()
                .userId(claims.get("userId", Long.class))
                .email(claims.get("email", String.class))
                .displayName(claims.get("displayName", String.class))
                .avatarUrl(claims.get("avatarUrl", String.class))
                .role(null) // Will be set separately if needed
                .build();
    }

    /**
     * Gets a specific claim from the token.
     *
     * @param token the JWT token string
     * @param claimName the name of the claim
     * @param claimType the type of the claim value
     * @return the claim value
     */
    public <T> T getClaim(String token, String claimName, Class<T> claimType) {
        Claims claims = parseToken(token);
        return claims.get(claimName, claimType);
    }
}



