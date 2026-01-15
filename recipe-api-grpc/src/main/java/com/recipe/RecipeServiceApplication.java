package com.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.TimeZone;

@SpringBootApplication
//@ComponentScan(basePackages = "com.recipe")
//@EnableJpaRepositories(basePackages = "com.recipe.data.jdbc.repository")
//@EntityScan(basePackages = "com.recipe.data.jdbc.model")
public class RecipeServiceApplication {
    public static void main(String[] args) {
        // Ensure the JVM default timezone uses a Postgres-recognized ID (Asia/Kolkata)
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(RecipeServiceApplication.class, args);
    }
}