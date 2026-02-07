package com.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.TimeZone;

@SpringBootApplication
@EntityScan(basePackages = {"com.recipe.data.auth.model"})
@EnableJpaRepositories(basePackages = {"com.recipe.data.auth.repository"})
public class RecipeAuthApplication {

    static void main(String[] args) {
        // Ensure the JVM default timezone uses a Postgres-recognized ID (Asia/Kolkata)
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(RecipeAuthApplication.class, args);
    }

}
