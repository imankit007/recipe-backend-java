package com.recipe.controller;

import com.recipe.dto.review.ReviewRequest;
import com.recipe.dto.review.ReviewResponse;
import com.recipe.service.ReviewService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recipe/{recipeId}/reviews")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Recipe Reviews")
public class RecipeReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> postReview(
            @PathVariable("recipeId") Long recipeId,
            @Valid @RequestBody ReviewRequest reviewRequest
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.postReview(recipeId, reviewRequest));
    }


}
