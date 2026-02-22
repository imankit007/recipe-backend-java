package com.recipe.api.gateway.converter;


import com.recipe.api.gateway.dto.review.ReviewRequest;
import com.recipe.api.gateway.dto.review.ReviewResponse;
import com.recipe.core.utils.EnumMapper;
import com.recipe.grpc.api.recipe.v1.PostReviewRequest;
import com.recipe.grpc.api.recipe.v1.PostReviewResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ReviewConverter {
    private final EnumMapper enumMapper;




    public PostReviewRequest toReviewGrpcRequest(ReviewRequest restRequest, Long recipeId) {
        PostReviewRequest.Builder postReviewRequestBuilder = PostReviewRequest.newBuilder();
        postReviewRequestBuilder.setRecipeId(recipeId);
        postReviewRequestBuilder.setComment(restRequest.comment());
        postReviewRequestBuilder.setRating(restRequest.rating());
        return postReviewRequestBuilder.build();
    }


    public ReviewResponse toRestReviewResponse(PostReviewResponse grpcResponse){
        return new ReviewResponse(
                grpcResponse.getReviewId(),
                grpcResponse.getRating(),
                grpcResponse.getComment()
        );
    }





}
