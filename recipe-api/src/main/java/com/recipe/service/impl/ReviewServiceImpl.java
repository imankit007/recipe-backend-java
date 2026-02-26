package com.recipe.service.impl;

import com.recipe.converter.ReviewConverter;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.model.RecipeReview;
import com.recipe.data.jdbc.repository.RecipeRepository;
import com.recipe.data.jdbc.repository.RecipeReviewRepository;
import com.recipe.dto.review.ReviewRequest;
import com.recipe.dto.review.ReviewResponse;
import com.recipe.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final RecipeReviewRepository recipeReviewRepository;

    private final RecipeRepository recipeRepository;

    private final ReviewConverter reviewConverter;

    @Override
    public ReviewResponse postReview(Long recipeId , ReviewRequest request) {
        final long userId = 100L;
        RecipeReview recipeReview = new RecipeReview();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(()-> new RuntimeException("Recipe not found"));
        recipeReview.setRecipe(recipe);
        recipeReview.setUserId(userId);
        recipeReview.setRating(request.rating());
        recipeReview.setComment(request.comment());
        RecipeReview savedReview = recipeReviewRepository.save(recipeReview);
        return reviewConverter.toPostReviewResponse(savedReview);
    }
}
