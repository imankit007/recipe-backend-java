package com.recipe.grpc;

import com.recipe.grpc.adapter.IngredientServiceGrpcAdapter;
import com.recipe.grpc.api.recipe.v1.CreateIngredientRequest;
import com.recipe.grpc.api.recipe.v1.CreateIngredientResponse;
import com.recipe.grpc.api.recipe.v1.IngredientServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;


@GrpcService
@RequiredArgsConstructor
public class IngredientServiceGrpcImpl extends IngredientServiceGrpc.IngredientServiceImplBase {

    private final IngredientServiceGrpcAdapter ingredientServiceGrpcAdapter;

    @Override
    public void createIngredient(CreateIngredientRequest request,
                                 StreamObserver<CreateIngredientResponse> responseObserver) {

        responseObserver.onNext(ingredientServiceGrpcAdapter.createIngredient(request));
        responseObserver.onCompleted();
    }

}
