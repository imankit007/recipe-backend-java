package com.recipe.data.jdbc.repository;

import com.recipe.data.jdbc.model.Ingredient;
import com.recipe.data.base.repository.BaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IngredientRepository extends BaseRepository<Ingredient> {

}
