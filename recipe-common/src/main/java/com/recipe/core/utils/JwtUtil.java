package com.recipe.core.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.util.Date;

@Slf4j
@Configuration
@AllArgsConstructor
public class JwtUtil {


    @Value("${security.jwt.key.private}")
    private Resource privateKey;

    @Value("${security.jwt.key.public}")
    private Resource publicKey;

    @Bean
    public KeyPair keyPair() throws Exception {
        return new KeyPair(
                PemUtils.readPublicKey(publicKey),
                PemUtils.readPrivateKey(privateKey)
        );
    }

}
