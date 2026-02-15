package com.recipe.grpc;

import com.recipe.grpc.adapter.RecipeServiceGrpcAdapter;
import com.recipe.grpc.api.recipe.v1.*;
import io.grpc.stub.StreamObserver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
@RequiredArgsConstructor
public class RecipeServiceGrpcImpl extends RecipeServiceGrpc.RecipeServiceImplBase {

    private final RecipeServiceGrpcAdapter recipeServiceGrpcAdapter;

    @Override
    public void getRecipe(GetRecipeRequest request, StreamObserver<GetRecipeResponse> responseObserver) {
        responseObserver.onNext(recipeServiceGrpcAdapter.getRecipe(request));
        responseObserver.onCompleted();
    }

    @Override
    public void createRecipe(CreateRecipeRequest request, StreamObserver<CreateRecipeResponse> responseObserver) {
        responseObserver.onNext(recipeServiceGrpcAdapter.createRecipe(request));
        responseObserver.onCompleted();
    }

    @Override
    public void updateRecipe(UpdateRecipeRequest request, StreamObserver<UpdateRecipeResponse> responseObserver) {
        responseObserver.onNext(recipeServiceGrpcAdapter.updateRecipe(request));
        responseObserver.onCompleted();
    }

    @Override
    public void deleteRecipe(DeleteRecipeRequest request, StreamObserver<DeleteRecipeResponse> responseObserver) {
        responseObserver.onNext(recipeServiceGrpcAdapter.deleteRecipe(request));
        responseObserver.onCompleted();
    }

    @Override
    @Transactional
    public void listRecipes(ListRecipesRequest request, StreamObserver<ListRecipesResponse> responseObserver) {
        responseObserver.onNext(recipeServiceGrpcAdapter.listRecipes(request));
        responseObserver.onCompleted();
    }
}
