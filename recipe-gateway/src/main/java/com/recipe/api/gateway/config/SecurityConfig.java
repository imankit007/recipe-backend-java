package com.recipe.api.gateway.config;

import com.recipe.core.utils.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("====== CUSTOM SECURITY FILTER CHAIN LOADED ======");
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth) -> auth.
                        requestMatchers(
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml", "/login").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout.disable())
                .oauth2ResourceServer(config ->
                        config.jwt(customizer ->
                                customizer.decoder(jwtDecoder())))
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(customizer -> customizer.disable())
                .build();
    }


    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(JwtUtil.getSecretKey()).build();
    }



}