package com.recipe.core.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.security.KeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Configuration
public class JwtUtil {

    @Value("${security.jwt.key.private}")
    private Resource privateKey;

    @Value("${security.jwt.key.public}")
    private Resource publicKey;

    @Bean
    public KeyPair keyPair() throws InvalidKeySpecException, IOException, NoSuchAlgorithmException {
        return new KeyPair(
                PemUtils.readPublicKey(publicKey),
                PemUtils.readPrivateKey(privateKey)
        );
    }

}
