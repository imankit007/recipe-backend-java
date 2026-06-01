package com.ankit007.recipe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayRouteConfig {

    @Value("${recipe.service.url:http://localhost:8081}")
    private String recipeServiceUrl;

    @Value("${user.service.url:http://localhost:8083}")
    private String userServiceUrl;


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                // Proxy API docs from recipe-service to gateway's proxy endpoint
                // Route for Recipe Service itself
                .route("recipe-service", r -> r
                        .path("/recipe-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(recipeServiceUrl))

                .route("user-service", r -> r
                        .path("/user-service/**")
                        .filters(f -> f.stripPrefix(1))
                        .uri(userServiceUrl))
                .build();
    }
}

