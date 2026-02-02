package com.recipe.api.gateway.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tools.jackson.databind.ObjectMapper;

@Configuration
@RequiredArgsConstructor
public class ApiGatewayOpenApiConfig {

    private final ApplicationContext applicationContext;

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

    // Customize the ModelResolver to use SNAKE_CASE naming strategy
    // This ensures that the OpenAPI schema reflects the expected JSON format
    // Springdoc uses Jackson's ObjectMapper for serialization by default
    @Bean
    public ModelResolver modelResolver() {
        com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        jacksonObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE);
        return new ModelResolver(jacksonObjectMapper);
    }

}
