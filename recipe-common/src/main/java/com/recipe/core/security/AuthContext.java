package com.recipe.core.security;

import com.recipe.core.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Context object that holds authenticated user information.
 * This object is stored in ThreadLocal for thread-safe access across the request lifecycle.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthContext {

    private Long userId;
    private String email;
    private String displayName;
    private String avatarUrl;
    private UserRole role;

}

