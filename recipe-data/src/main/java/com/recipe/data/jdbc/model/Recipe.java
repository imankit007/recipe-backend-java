package com.recipe.data.jdbc.model;


import com.recipe.core.data.BaseEntity;
import com.recipe.core.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "RECIPES")
@Data()
public class Recipe extends BaseEntity {

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", length = 2000)
    private String description;

    @Column(name = "PREP_TIME_MINUTES")
    private Integer prepTimeMinutes;

    @Column(name = "COOK_TIME_MINUTES")
    private Integer cookTimeMinutes;

    @Column(name = "SERVINGS")
    private Integer servings;

    @Enumerated(EnumType.STRING)
    @Column(name = "DIFFICULTY_LEVEL", nullable = false)
    private DifficultyLevel difficultyLevel;

    @OneToMany(
            mappedBy = "recipe",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("stepNumber ASC")
    private List<RecipeStep> steps = new ArrayList<>();


}
