package com.recipe.grpc.adapter;


import com.recipe.grpc.api.recipe.v1.PostReviewRequest;
import com.recipe.grpc.api.recipe.v1.PostReviewResponse;

public interface ReviewServiceGrpcAdapter {
    PostReviewResponse postReview(PostReviewRequest request);
}
