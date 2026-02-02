package com.recipe.data.jdbc.model;

import com.recipe.data.jdbc.model.base.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "RECIPE_REVIEWS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_RECIPE_REVIEW_USER_RECIPE", columnNames = {"RECIPE_ID", "USER_ID"})
    },
    indexes = {
        @Index(name = "IDX_RECIPE_REVIEW_RECIPE", columnList = "RECIPE_ID"),
        @Index(name = "IDX_RECIPE_REVIEW_USER", columnList = "RECIPE_ID, USER_ID"),
        @Index(name = "IDX_REVIEW_RECIPE_CREATED", columnList = "RECIPE_ID, CREATED_AT DESC"),
        @Index(name = "IDX_REVIEW_RATING", columnList = "RECIPE_ID, RATING")

    }
)
@EqualsAndHashCode(callSuper = true,exclude = {"user", "recipe"})
public class RecipeReview extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RECIPE_REVIEW_USER"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", nullable = false, foreignKey = @ForeignKey(name = "FK_RECIPE_REVIEW_RECIPE"))
    private Recipe recipe;

    @Min(1)
    @Max(5)
    @Column(name = "RATING", nullable = false)
    private int rating;

    @Column(name = "COMMENT", length = 2000)
    private String comment;

    @Column(name = "HELPFUL_COUNT", nullable = false)
    private long helpfulCount = 0;

}
