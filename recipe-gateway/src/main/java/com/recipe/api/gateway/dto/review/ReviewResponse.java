package com.recipe.api.gateway.dto.review;

public record ReviewResponse(
        Long id,
        Double rating,
        String comment
) {

}
