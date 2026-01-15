package com.recipe.data.jdbc.model;


import com.recipe.core.data.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "RECIPES")
@Data()
public class Recipe extends BaseEntity {

    private String title;

    private String description;

    private Integer prepTimeMinutes;

    private Integer cookTimeMinutes;

    private Integer servings;

    //TODO: difficulty level

    //TODO: recipe steps

    //TODO


}
