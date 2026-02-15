package com.recipe.core.error;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public enum ErrorCode {
    RECIPE_NOT_FOUND("recipe.not_found"),
    INGREDIENT_ALREADY_EXISTS("ingredient.already_exists"),
    INTERNAL_ERROR("internal.error");

    private final String key;

    private static final Map<String, ErrorCode> KEY_MAP = new ConcurrentHashMap<>();

    static {
        Arrays.stream(values()).forEach(c -> KEY_MAP.put(c.key, c));
    }

    ErrorCode(String key) {
        this.key = key;
    }

    public static Optional<ErrorCode> fromKey(String key) {
        return Optional.ofNullable(KEY_MAP.get(key));
    }

    @Override
    public String toString() {
        return key;
    }
}
