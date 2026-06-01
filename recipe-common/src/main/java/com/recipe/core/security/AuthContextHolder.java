package com.recipe.core.security;

import com.recipe.core.enums.UserRole;

/**
 * Thread-safe holder for AuthContext using ThreadLocal.
 * Provides static methods to set, get, and clear the authenticated user context.
 *
 * Usage:
 * - Set context: AuthContextHolder.setContext(authContext);
 * - Get context: AuthContext context = AuthContextHolder.getContext();
 * - Clear context: AuthContextHolder.clearContext();
 */
public class AuthContextHolder {

    private static final ThreadLocal<AuthContext> contextThreadLocal = new ThreadLocal<>();

    private AuthContextHolder() {
        // Private constructor to hide the implicit public one
    }

    /**
     * Sets the AuthContext for the current thread.
     *
     * @param context the authentication context
     */
    public static void setContext(AuthContext context) {
        contextThreadLocal.set(context);
    }

    /**
     * Gets the AuthContext for the current thread.
     *
     * @return the authentication context, or null if not set
     */
    public static AuthContext getContext() {
        return contextThreadLocal.get();
    }

    /**
     * Gets the authenticated user ID from the context.
     *
     * @return the user ID, or null if context is not set
     */
    public static Long getUserId() {
        AuthContext context = contextThreadLocal.get();
        return context != null ? context.getUserId() : null;
    }

    /**
     * Gets the authenticated user email from the context.
     *
     * @return the user email, or null if context is not set
     */
    public static String getEmail() {
        AuthContext context = contextThreadLocal.get();
        return context != null ? context.getEmail() : null;
    }

    /**
     * Gets the authenticated user display name from the context.
     *
     * @return the user display name, or null if context is not set
     */
    public static String getDisplayName() {
        AuthContext context = contextThreadLocal.get();
        return context != null ? context.getDisplayName() : null;
    }

    /**
     * Gets the authenticated user role from the context.
     *
     * @return the user role, or null if context is not set
     */
    public static UserRole getRole() {
        AuthContext context = contextThreadLocal.get();
        return context != null ? context.getRole() : null;
    }

    /**
     * Clears the AuthContext for the current thread.
     * Should be called in a finally block or filter cleanup to prevent memory leaks.
     */
    public static void clearContext() {
        contextThreadLocal.remove();
    }

    /**
     * Checks if an authenticated context is present in the current thread.
     *
     * @return true if context is set and user ID is not null, false otherwise
     */
    public static boolean isAuthenticated() {
        AuthContext context = contextThreadLocal.get();
        return context != null && context.getUserId() != null;
    }
}

