package com.recipe.data.jdbc.model;


import com.recipe.core.enums.Unit;
import com.recipe.data.base.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true, exclude = {"recipe", "ingredient"})
@Entity
@Table(name = "RECIPE_INGREDIENTS")
public class RecipeIngredient extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "RECIPE_ID",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_RECIPE_INGREDIENT_RECIPE")
    )
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST})
    @JoinColumn(
        name = "INGREDIENT_ID",
        nullable = false,
        foreignKey = @ForeignKey(name = "FK_RECIPE_INGREDIENT_INGREDIENT")
    )
    private Ingredient ingredient;

    @Column(name = "QUANTITY", nullable = false)
    private BigDecimal quantity;

    @Column(name = "UNIT", nullable = false)
    @Enumerated(EnumType.STRING)
    private Unit unit;
}
