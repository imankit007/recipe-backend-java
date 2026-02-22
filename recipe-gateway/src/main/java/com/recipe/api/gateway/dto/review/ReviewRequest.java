package com.recipe.api.gateway.dto.review;


public record ReviewRequest(
        String comment,
        Double rating
){

}
