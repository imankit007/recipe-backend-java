package com.recipe.core.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.security.KeyPair;

@Configuration
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
