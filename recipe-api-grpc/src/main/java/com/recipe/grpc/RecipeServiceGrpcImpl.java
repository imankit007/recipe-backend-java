package com.recipe.grpc;

import com.recipe.core.utils.PageUtils;
import com.recipe.data.jdbc.repository.RecipeRepository;
import com.recipe.grpc.adapter.RecipeServiceGrpcAdapter;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.grpc.server.service.GrpcService;
import com.recipe.grpc.api.recipe.v1.*;


import java.awt.print.Pageable;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toList;

@GrpcService
@Slf4j
public class RecipeServiceGrpcImpl extends RecipeServiceGrpc.RecipeServiceImplBase {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeServiceGrpcAdapter recipeServiceGrpcAdapter;

    @Override
    public void getRecipe(GetRecipeRequest request, StreamObserver<GetRecipeResponse> responseObserver) {
        log.info("Received request to recipe request of type: {}, content: {}", request.getClass().getName(), request);
        responseObserver.onNext(recipeServiceGrpcAdapter.getRecipe(request));
        responseObserver.onCompleted();
    }

//    @Override
//    public void createRecipe(CreateRecipeRequest request, StreamObserver<Recipe> responseObserver) {
//        Recipe incoming = request.getRecipe();
//        String id = incoming.getId();
//        if (id == null || id.isEmpty()) {
//            id = UUID.randomUUID().toString();
//        }
//        Recipe stored = Recipe.newBuilder()
//                .setId(id)
//                .setTitle(incoming.getTitle())
//                .addAllIngredients(incoming.getIngredientsList())
//                .setInstructions(incoming.getInstructions())
//                .setPrepMinutes(incoming.getPrepMinutes())
//                .setCookMinutes(incoming.getCookMinutes())
//                .build();
//        store.put(id, stored);
//        log.info("Created recipe {} ({}).", stored.getTitle(), stored.getId());
//        responseObserver.onNext(stored);
//        responseObserver.onCompleted();
//    }
//
//    @Override
//    public void updateRecipe(UpdateRecipeRequest request, StreamObserver<Recipe> responseObserver) {
//        Recipe incoming = request.getRecipe();
//        String id = incoming.getId();
//        if (id == null || id.isEmpty() || !store.containsKey(id)) {
//            responseObserver.onError(Status.NOT_FOUND.withDescription("Recipe not found: " + id).asRuntimeException());
//            return;
//        }
//        Recipe updated = Recipe.newBuilder()
//                .setId(id)
//                .setTitle(incoming.getTitle())
//                .addAllIngredients(incoming.getIngredientsList())
//                .setInstructions(incoming.getInstructions())
//                .setPrepMinutes(incoming.getPrepMinutes())
//                .setCookMinutes(incoming.getCookMinutes())
//                .build();
//        store.put(id, updated);
//        log.info("Updated recipe {} ({}).", updated.getTitle(), updated.getId());
//        responseObserver.onNext(updated);
//        responseObserver.onCompleted();
//    }

//    @Override
//    public void deleteRecipe(DeleteRecipeRequest request, StreamObserver<Empty> responseObserver) {
//        String id = request.getId();
//        Recipe removed = store.remove(id);
//        if (removed == null) {
//            responseObserver.onError(Status.NOT_FOUND.withDescription("Recipe not found: " + id).asRuntimeException());
//            return;
//        }
//        log.info("Deleted recipe {} ({}).", removed.getTitle(), removed.getId());
//        responseObserver.onNext(Empty.getDefaultInstance());
//        responseObserver.onCompleted();
//    }

    @Override
    @Transactional
    public void listRecipes(ListRecipesRequest request, StreamObserver<ListRecipesResponse> responseObserver) {
        log.info("Received request to recipe request of type: {}, content: {}", request.getClass().getName(), request);
        responseObserver.onNext(recipeServiceGrpcAdapter.listRecipes(request));
        responseObserver.onCompleted();
    }
}
