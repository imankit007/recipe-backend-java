package com.recipeauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class RecipeAuthApplication {

    static void main(String[] args) {
        // Ensure the JVM default timezone uses a Postgres-recognized ID (Asia/Kolkata)
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
        SpringApplication.run(RecipeAuthApplication.class, args);
    }

}
