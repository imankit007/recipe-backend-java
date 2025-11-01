package com.recipe.data;

import com.recipe.core.data.BaseDo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "RECIPES")
@Data
public class Recipe extends BaseDo {

    @Column(name = "NAME")
    String name;
}
