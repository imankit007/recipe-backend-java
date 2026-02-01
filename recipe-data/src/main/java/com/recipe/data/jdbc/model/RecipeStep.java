package com.recipe.data.jdbc.model;

import com.recipe.data.jdbc.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table
public class RecipeStep extends BaseEntity {

    @Column(name = "STEP_NUMBER", nullable = false)
    private int stepNumber;

    @Column(name = "INSTRUCTION", nullable = false, length = 2000)
    private String instruction;

    @Column(name = "DURATION_MINUTES")
    private Integer durationMinutes;

    @Column(name = "MEDIA_URL", length = 500)
    private String mediaUrl;

    @ManyToOne
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RECIPE_STEP_RECIPE"))
    private Recipe recipe;
}
