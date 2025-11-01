package com.recipe.api.gateway.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayOpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("Recipe API Gateway")
                        .description("API Gateway exposing REST endpoints backed by gRPC services")
                        .version("v0.1")
                        .contact(new Contact().name("Recipe Team").email("devops@example.com"))
                );
    }
}

