package com.recipe.grpc;

import com.recipe.grpc.adapter.ReviewServiceGrpcAdapter;
import com.recipe.grpc.api.recipe.v1.PostReviewRequest;
import com.recipe.grpc.api.recipe.v1.PostReviewResponse;
import com.recipe.grpc.api.recipe.v1.ReviewServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.AllArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@AllArgsConstructor
public class ReviewServiceGrpcImpl extends ReviewServiceGrpc.ReviewServiceImplBase {

    private ReviewServiceGrpcAdapter reviewServiceGrpcAdapter;

    @Override
    public void postReview(PostReviewRequest request, StreamObserver<PostReviewResponse> responseObserver) {
        responseObserver.onNext(reviewServiceGrpcAdapter.postReview(request));
        responseObserver.onCompleted();
    }
}
