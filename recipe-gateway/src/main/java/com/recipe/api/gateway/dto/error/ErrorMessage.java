package com.recipe.api.gateway.dto.error;

import java.util.Map;

public record ErrorMessage(
        String message,
        String code,
        String reason,
        Map<String, String> metadata
) {
}
