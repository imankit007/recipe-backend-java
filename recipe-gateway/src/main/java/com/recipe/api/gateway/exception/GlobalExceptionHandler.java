package com.recipe.api.gateway.exception;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.rpc.ErrorInfo;
import com.recipe.api.gateway.dto.error.ErrorMessage;
import com.recipe.api.gateway.i18n.MessageResolver;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.StatusProto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Collections;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageResolver messageResolver;

    public GlobalExceptionHandler(MessageResolver messageResolver) {
        this.messageResolver = messageResolver;
    }

    // Map by Status.Code to avoid failed lookups due to Status instance identity
    public static final Map<Status.Code, HttpStatus> grpcToHttpStatusMap = Map.of(
            Status.Code.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST,
            Status.Code.NOT_FOUND, HttpStatus.NOT_FOUND,
            Status.Code.ALREADY_EXISTS, HttpStatus.CONFLICT,
            Status.Code.PERMISSION_DENIED, HttpStatus.FORBIDDEN,
            Status.Code.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED,
            Status.Code.INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR,
            Status.Code.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR,
            Status.Code.UNIMPLEMENTED, HttpStatus.NOT_IMPLEMENTED
    );

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ErrorMessage> handleGrpcException(StatusRuntimeException ex) {
        ErrorInfo errorInfo = null;
        try {
            com.google.rpc.Status statusProto = StatusProto.fromThrowable(ex);
            if (statusProto != null) {
                for (Any detail : statusProto.getDetailsList()) {
                    if (detail.is(ErrorInfo.class)) {
                        errorInfo = detail.unpack(ErrorInfo.class);
                        break;
                    }
                }
            }
        } catch (InvalidProtocolBufferException e) {
            log.warn("Failed to unpack gRPC ErrorInfo details", e);
        }

        HttpStatus httpStatus = grpcToHttpStatusMap.getOrDefault(
                ex.getStatus() != null ? ex.getStatus().getCode() : null,
                HttpStatus.INTERNAL_SERVER_ERROR
        );

        // Log the exception and include stack trace
        log.error("gRPC exception: status={}, description={}", ex.getStatus(), ex.getStatus() != null ? ex.getStatus().getDescription() : null, ex);

        String message = null;
        Map<String, String> metadata = Collections.emptyMap();

        if (errorInfo != null) {
            metadata = errorInfo.getMetadataMap();
            // If the interceptor attached an error-code, attempt localized resolution with params
            if (metadata.containsKey("error-code")) {
                String code = metadata.get("error-code");
                String lang = metadata.getOrDefault("error-lang", "en");

                // build params map from entries prefixed with param-
                Map<String, String> params = new HashMap<>();
                metadata.forEach((k, v) -> {
                    if (k.startsWith("param-")) {
                        params.put(k.substring("param-".length()), v);
                    }
                });

                message = messageResolver.resolve(code, Locale.forLanguageTag(lang), params);
            }
        }

        // Fallback selection if message still null
        if (message == null) {
            if (ex.getStatus() != null && ex.getStatus().getDescription() != null && !ex.getStatus().getDescription().isBlank()) {
                message = ex.getStatus().getDescription();
            } else if (metadata.containsKey("error-message")) {
                message = metadata.get("error-message");
            } else if (ex.getMessage() != null && !ex.getMessage().isBlank()) {
                message = ex.getMessage();
            } else {
                message = "gRPC error"; // guaranteed non-null fallback
            }
        }

        ErrorMessage errorMessage = new ErrorMessage(
                message,
                errorInfo != null ? errorInfo.getDomain() : null,
                errorInfo != null ? errorInfo.getReason() : null,
                metadata
        );

        return ResponseEntity.status(httpStatus).body(errorMessage);
    }
}
