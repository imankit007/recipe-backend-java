package com.recipe.api.gateway.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@SecuritySchemes(value = {
        @SecurityScheme(name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
                , description = "JWT Bearer token authentication")
}
)
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

    // Customize the ModelResolver to use CAMEL_CASE naming strategy
    // so OpenAPI request/response models use camelCase as requested.
    @Bean
    public ModelResolver modelResolver() {
        com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
        // Use the default camel case strategy (no transformation) to keep property names in camelCase
        jacksonObjectMapper.setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE);
        return new ModelResolver(jacksonObjectMapper);
    }

}
