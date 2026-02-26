package com.recipe.dto.review;

public record ReviewResponse(
        Long id,
        Double rating,
        String comment
) {

}
