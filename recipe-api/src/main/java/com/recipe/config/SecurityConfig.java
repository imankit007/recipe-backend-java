package com.recipe.config;

import com.recipe.core.security.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration for registering JWT authentication filter.
 * The filter will be applied to all requests except those bypassed in shouldNotFilter.
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    /**
     * Registers the JWT auth filter with the servlet filter chain.
     *
     * @return FilterRegistrationBean for JwtAuthFilter
     */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.cors(
                cors -> cors.disable()
                )
                .csrf(
                csrf -> csrf.disable()
        ).authorizeHttpRequests(
                auth ->
                        auth.requestMatchers("/v3/api-docs")
                                .permitAll()
                .anyRequest().permitAll()
        ).addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


}

