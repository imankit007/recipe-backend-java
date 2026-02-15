package com.recipe.core.exception;

import com.recipe.core.error.AppErrorCode;
import io.grpc.Status;
import lombok.Getter;

import java.util.Map;

@Getter
public class GrpcException extends RuntimeException {

    private final Status status;
    private final AppErrorCode code; // now typed to AppErrorCode
    private final Map<String, String> params; // optional parameters for message formatting
    private final String language; // optional language tag, e.g., "en", "fr"

    public GrpcException(AppErrorCode error, Map<String, String> params, String language, String message) {
        super(message);
        this.status = error.getStatus();
        this.code = error;
        this.params = params;
        this.language = language;
    }


}
