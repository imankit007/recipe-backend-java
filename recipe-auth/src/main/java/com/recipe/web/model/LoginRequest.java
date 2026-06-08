package com.recipe.web.model;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        defaultValue = """
                {
                    "username": "ankit@gmail.com",
                    "password": "aerohive"
                }
                """,
        description = "Login request payload containing username and password"
)
public record LoginRequest (
    String username,
    String password
){}
