package com.recipe.service.recipe;

import com.recipe.grpc.api.recipe.v1.ListRecipesRequest;
import com.recipe.grpc.api.recipe.v1.ListRecipesResponse;
import com.recipe.grpc.api.recipe.v1.Recipe;
import com.recipe.grpc.api.recipe.v1.RecipeServiceGrpc;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class RecipeServiceGrpcImpl extends RecipeServiceGrpc.RecipeServiceImplBase {

    // In-memory store: id -> Recipe
    private final Map<String, Recipe> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void initData() {
        Recipe recipe1 = Recipe.newBuilder()
                .setId("1")
                .setTitle("Spaghetti Bolognese")
                .build();
        store.put(recipe1.getId(), recipe1);

        Recipe recipe2 = Recipe.newBuilder()
                .setId("2")
                .setTitle("Chicken Curry")
                .build();
        store.put(recipe2.getId(), recipe2);

        log.info("Initialized sample recipes.");
    }

//    @Override
//    public void getRecipe(GetRecipeRequest request, StreamObserver<Recipe> responseObserver) {
//        String id = request.getId();
//        Recipe found = store.get(id);
//        if (found == null) {
//            responseObserver.onError(Status.NOT_FOUND.withDescription("Recipe not found: " + id).asRuntimeException());
//            return;
//        }
//        responseObserver.onNext(found);
//        responseObserver.onCompleted();
//    }

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
    public void listRecipes(ListRecipesRequest request, StreamObserver<ListRecipesResponse> responseObserver) {
        Collection<Recipe> recipes = store.values();
        ListRecipesResponse response = ListRecipesResponse.newBuilder()
                .addAllRecipes(recipes)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
