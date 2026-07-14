package com.recipe.core.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader("Authorization");

            if (authHeader != null && authHeader.startsWith(BEARER_PREFIX)) {
                String token = authHeader.substring(BEARER_PREFIX.length());

                // Validate and parse token
                if (jwtTokenUtil.validateToken(token)) {
                    try {
                        // Extract user information from token
                        AuthContext authContext = jwtTokenUtil.extractAuthContext(token);

                        // Store in ThreadLocal context
                        AuthContextHolder.setContext(authContext);

                    } catch (Exception _) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        response.getWriter().write("Invalid token");
                        return;
                    }
                } else {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("Token validation failed");
                    return;
                }
            } else {
                // No Authorization header - reject request for protected endpoints
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Missing Authorization header");
                return;
            }

            filterChain.doFilter(request, response);
        } finally {
            // Clean up ThreadLocal to prevent memory leaks
            AuthContextHolder.clearContext();
        }
    }

    // Don't apply this filter for Swagger/OpenAPI and related static endpoints
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null) return false;

        // Extract the path without query parameters
        if (path.contains("?")) {
            path = path.substring(0, path.indexOf("?"));
        }

        return path.equals("/swagger-ui.html")
                || path.startsWith("/swagger-ui/")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-resources")
                || path.startsWith("/webjars/")
                || path.startsWith("/webjars")
                || path.equals("/login")
                || path.startsWith("/auth/")
                || path.startsWith("/actuator/")
                || path.equals("/")
                || path.startsWith("/health")
                ;
    }
}

