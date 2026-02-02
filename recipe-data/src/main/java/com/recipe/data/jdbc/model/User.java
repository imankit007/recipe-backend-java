package com.recipe.data.jdbc.model;

import com.recipe.data.jdbc.model.base.BaseEntity;
import com.recipe.core.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "USERS",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_EMAIL", columnNames = "EMAIL")
    },
    indexes = {
        @Index(name = "IDX_USER_DISPLAY_NAME", columnList = "EMAIL")
    }
)
@EqualsAndHashCode(callSuper = true, exclude = {})
public class User extends BaseEntity {

    @Column(name = "DISPLAY_NAME", nullable = false, unique = true)
    private String displayName;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String passwordHash;

    @Column(name = "AVATAR_URL", length = 500)
    private String avatarUrl;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private UserRole role = UserRole.USER;

}
