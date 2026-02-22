package com.recipe.grpc.adapter.impl;

import com.recipe.converter.ReviewConverter;
import com.recipe.core.error.RecipeErrorCode;
import com.recipe.core.exception.GrpcException;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.model.RecipeReview;
import com.recipe.data.jdbc.repository.RecipeRepository;
import com.recipe.data.jdbc.repository.RecipeReviewRepository;
import com.recipe.grpc.adapter.ReviewServiceGrpcAdapter;
import com.recipe.grpc.api.recipe.v1.PostReviewRequest;
import com.recipe.grpc.api.recipe.v1.PostReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;


@AllArgsConstructor
@Service
public class ReviewServiceGrpcAdapterImpl implements ReviewServiceGrpcAdapter {

    private final RecipeReviewRepository recipeReviewRepository;

    private final RecipeRepository recipeRepository;

    private final ReviewConverter reviewConverter;

    @Override
    public PostReviewResponse postReview(PostReviewRequest request) {
        final long recipeId = request.getRecipeId();
        final long userId = 100L;
        RecipeReview recipeReview = new RecipeReview();
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(()-> new GrpcException(RecipeErrorCode.RECIPE_NOT_FOUND, Map.of("id", String.valueOf(recipeId)), "en", "Recipe not found"));
        recipeReview.setRecipe(recipe);
        recipeReview.setUserId(userId);
        recipeReview.setRating(request.getRating());
        recipeReview.setComment(request.getComment());
        RecipeReview savedReview = recipeReviewRepository.save(recipeReview);
        return reviewConverter.toPostReviewResponse(savedReview);
    }
}
