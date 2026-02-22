package com.recipe.converter;


import com.recipe.data.jdbc.model.RecipeReview;
import com.recipe.grpc.api.recipe.v1.PostReviewRequest;
import com.recipe.grpc.api.recipe.v1.PostReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewConverter {

    public PostReviewResponse toPostReviewResponse(RecipeReview recipeReview){

        PostReviewResponse.Builder builder = PostReviewResponse.newBuilder();
        builder.setRecipeId(recipeReview.getRecipe().getId());
        builder.setReviewId(recipeReview.getId());
        builder.setComment(recipeReview.getComment());
        builder.setRating(recipeReview.getRating());
        return builder.build();


    }


}

