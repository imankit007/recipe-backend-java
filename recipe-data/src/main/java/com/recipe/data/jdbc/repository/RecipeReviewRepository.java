package com.recipe.data.jdbc.repository;

import com.recipe.data.base.repository.BaseRepository;
import com.recipe.data.jdbc.model.Recipe;
import com.recipe.data.jdbc.model.RecipeReview;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeReviewRepository extends BaseRepository<RecipeReview> {

}
