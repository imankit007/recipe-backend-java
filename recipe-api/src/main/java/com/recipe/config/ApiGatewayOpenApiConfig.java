package com.recipe.config;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
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
        ObjectMapper jacksonObjectMapper = JsonMapper.builder().enable(
                MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS
        ).propertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE).build();
        return new ModelResolver(jacksonObjectMapper);
    }

}
