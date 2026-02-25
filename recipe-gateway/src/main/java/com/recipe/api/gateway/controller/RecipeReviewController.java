//package com.recipe.api.gateway.recipe.controller;
//
//import com.recipe.api.gateway.converter.RecipeConverter;
//import com.recipe.api.gateway.converter.ReviewConverter;
//import com.recipe.api.gateway.recipe.dto.review.ReviewRequest;
//import com.recipe.api.gateway.recipe.dto.review.ReviewResponse;
//import com.recipe.api.gateway.grpc.client.RecipeGrpcClient;
//import com.recipe.grpc.api.recipe.v1.PostReviewRequest;
//import com.recipe.grpc.api.recipe.v1.PostReviewResponse;
//import com.recipe.grpc.api.recipe.v1.RecipeServiceGrpc;
//import com.recipe.grpc.api.recipe.v1.ReviewServiceGrpc;
//import io.swagger.v3.oas.annotations.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import jakarta.websocket.server.PathParam;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//
//@RestController
//@RequestMapping("/recipe/{recipeId}/reviews")
//@AllArgsConstructor
//@SecurityRequirement(name = "bearerAuth")
//@Tag(name = "Recipe Reviews")
//public class RecipeReviewController {
//
//    private final RecipeGrpcClient grpcClient;
//
//    private final ReviewConverter reviewConverter;
//
//    public ReviewServiceGrpc.ReviewServiceBlockingStub getClient() {
//        return grpcClient.review();
//    }
//
//    @PostMapping
//    public ResponseEntity<ReviewResponse> postReview(
//            @PathVariable("recipeId") Long recipeId,
//            @Valid @RequestBody ReviewRequest reviewRequest
//    ) {
//        PostReviewRequest grpcRequest = reviewConverter.toReviewGrpcRequest(reviewRequest, recipeId);
//        PostReviewResponse grpcResponse = getClient().postReview(grpcRequest);
//        ReviewResponse response = reviewConverter.toRestReviewResponse(grpcResponse);
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//
//}
