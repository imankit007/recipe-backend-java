package com.recipe.auth.util;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
@AllArgsConstructor
public class JwtTokenProvider {

    private final KeyPair keyPair;

    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(keyPair.getPrivate(), SignatureAlgorithm.RS256)
                .compact();
    }

}
