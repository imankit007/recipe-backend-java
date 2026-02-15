package com.recipe.core.error;


import io.grpc.Status;
import lombok.Getter;

@Getter
public enum AuthErrorCode implements AppErrorCode {
    UNAUTHORIZED("auth.unauthorized", 3001, "Not authorized",Status.UNAUTHENTICATED ),
    INVALID_TOKEN("auth.invalid_token", 3002, "Invalid token", Status.UNAUTHENTICATED);

    private final String key;
    private final Integer id;
    private final String defaultMessage;
    private final Status status;

    AuthErrorCode(String key, Integer id, String defaultMessage, Status status) {
        this.key = key;
        this.id = id;
        this.defaultMessage = defaultMessage;
        this.status = status;
    }

}

