package com.recipe.data.jdbc.repository;

import com.recipe.data.base.repository.BaseRepository;
import com.recipe.data.jdbc.model.Recipe;
import org.springframework.stereotype.Repository;


@Repository
public interface RecipeRepository extends BaseRepository<Recipe> {

}
