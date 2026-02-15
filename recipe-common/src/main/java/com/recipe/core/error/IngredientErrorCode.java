package com.recipe.core.error;


import io.grpc.Status;
import lombok.Getter;

@Getter
public enum IngredientErrorCode implements AppErrorCode {
    INGREDIENT_ALREADY_EXISTS("ingredient.already_exists", 2001, "Ingredient already exists", Status.ALREADY_EXISTS),
    INGREDIENT_NOT_FOUND("ingredient.not_found", 2002, "Ingredient not found",Status.NOT_FOUND);

    private final String key;
    private final Integer id;
    private final String defaultMessage;
    private final Status status;

    IngredientErrorCode(String key, Integer id, String defaultMessage, Status status) {
        this.key = key;
        this.id = id;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }

}

