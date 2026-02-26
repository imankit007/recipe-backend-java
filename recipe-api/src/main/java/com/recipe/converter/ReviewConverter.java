package com.recipe.converter;


import com.recipe.data.jdbc.model.RecipeReview;
import com.recipe.dto.review.ReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewConverter {
    public ReviewResponse toPostReviewResponse(RecipeReview recipeReview) {
        return new ReviewResponse(
                recipeReview.getId(),
                recipeReview.getRating(),
                recipeReview.getComment()
        );
    }


}
