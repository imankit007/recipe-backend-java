package com.recipe.data.jdbc.model;

import com.recipe.data.base.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "INGREDIENTS")
public class Ingredient extends BaseEntity {

    @Column(name = "NAME", nullable = false)
    private String name;
}

