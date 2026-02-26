package com.recipe.service;


import com.recipe.dto.review.ReviewRequest;
import com.recipe.dto.review.ReviewResponse;

public interface ReviewService {

    ReviewResponse postReview(Long recipeId, ReviewRequest reviewRequest);

}
