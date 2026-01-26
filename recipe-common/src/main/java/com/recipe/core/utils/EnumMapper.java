package com.recipe.core.utils;


import com.recipe.core.enums.RecipeDifficultyLevel;
import com.recipe.grpc.api.recipe.v1.RecipeDifficulty;
import org.springframework.stereotype.Component;

@Component
public class EnumMapper {

    public RecipeDifficultyLevel toDomain(RecipeDifficulty protoEnum) {
        return switch (protoEnum) {
            case RECIPE_DIFFICULTY_EASY -> RecipeDifficultyLevel.EASY;
            case RECIPE_DIFFICULTY_MEDIUM -> RecipeDifficultyLevel.MEDIUM;
            case RECIPE_DIFFICULTY_HARD -> RecipeDifficultyLevel.HARD;
            default -> throw new IllegalArgumentException("Unknown RecipeDifficultyEnum: " + protoEnum);
        };
    }
    
    public RecipeDifficulty toProto(RecipeDifficultyLevel protoEnum) {
        return switch (protoEnum) {
            case EASY -> RecipeDifficulty.RECIPE_DIFFICULTY_EASY;
            case MEDIUM -> RecipeDifficulty.RECIPE_DIFFICULTY_MEDIUM;
            case HARD -> RecipeDifficulty.RECIPE_DIFFICULTY_HARD;
        };
    }

}
