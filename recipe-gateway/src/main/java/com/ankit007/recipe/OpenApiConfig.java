package com.ankit007.recipe;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recipe API Gateway")
                        .version("v0.1")
                        .description("API Gateway for Recipe Services")
                        .contact(new Contact()
                                .name("Recipe Team")
                                .email("devops@example.com")))
                .addServersItem(new Server()
                        .url("http://localhost:8082")
                        .description("Development Server"));
    }
}

