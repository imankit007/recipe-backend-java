package com.recipe.core.utils;

public final class ProtoSafeUtils {

    private ProtoSafeUtils() {
        // prevent instantiation
    }

    public static String safe(String s) {
        return s == null ? "" : s;
    }

    public static int safe(Integer i) {
        return i == null ? 0 : i;
    }

}
