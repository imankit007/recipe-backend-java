package com.recipe.api.gateway.exception;


import com.recipe.api.gateway.dto.error.ErrorMessage;
import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    public static Map<Status, HttpStatus> grpcToHttpStatusMap = Map.of(
            Status.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST,
            Status.NOT_FOUND, HttpStatus.NOT_FOUND,
            Status.ALREADY_EXISTS, HttpStatus.CONFLICT,
            Status.PERMISSION_DENIED, HttpStatus.FORBIDDEN,
            Status.UNAUTHENTICATED, HttpStatus.UNAUTHORIZED,
            Status.INTERNAL, HttpStatus.INTERNAL_SERVER_ERROR,
            Status.UNKNOWN, HttpStatus.INTERNAL_SERVER_ERROR,
            Status.UNIMPLEMENTED, HttpStatus.NOT_IMPLEMENTED
    );

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ErrorMessage> handleGrpcException(StatusRuntimeException ex) {
        HttpStatus httpStatus = grpcToHttpStatusMap.getOrDefault(ex.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
        assert ex.getTrailers() != null;
        log.error(ex.getMessage(), ex.getTrailers());
        String message = ex.getTrailers().get(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER));
        message = "Something went wrong";
        ErrorMessage errorMessage = new ErrorMessage(message);
        return ResponseEntity.status(httpStatus).body(errorMessage);
    }


}
