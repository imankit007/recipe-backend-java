package com.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
public class RecipeApiApplication {
    static void main(String[] args) {
        SpringApplication.run(RecipeApiApplication.class, args);
    }
}