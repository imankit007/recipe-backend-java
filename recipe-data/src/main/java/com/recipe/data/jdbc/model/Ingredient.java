package com.recipe.data.jdbc.model;

import com.recipe.data.base.model.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "INGREDIENTS",
        uniqueConstraints = {
                @UniqueConstraint(name = "UK_INGREDIENT_NAME", columnNames = "NAME")
        },
        indexes = {
        @Index(name = "IDX_INGREDIENT_NAME", columnList = "NAME")
})
public class Ingredient extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;
}

