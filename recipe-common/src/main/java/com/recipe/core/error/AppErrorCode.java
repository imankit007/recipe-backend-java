package com.recipe.core.error;

import io.grpc.Status;

public interface AppErrorCode {
    String getKey();
    default Integer getId() { return null; }
    default String getDefaultMessage() { return null; }
    Status getStatus();

}

