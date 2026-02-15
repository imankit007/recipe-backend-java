package com.recipe.interceptor;

import com.google.protobuf.Any;
import com.google.rpc.ErrorInfo;
import com.recipe.core.exception.GrpcException;
import io.grpc.*;
import io.grpc.protobuf.StatusProto;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@GlobalServerInterceptor
public class GrpcExceptionInterceptor implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
            ServerCall<ReqT,RespT> call,
            Metadata headers,
            ServerCallHandler<ReqT, RespT> next) {

        ServerCall.Listener<ReqT> listener = next.startCall(call, headers);

        return new ForwardingServerCallListener
                .SimpleForwardingServerCallListener<>(listener) {

            @Override
            public void onHalfClose() {
                try {
                    super.onHalfClose();
                } catch (Exception ex) {
                    handleException(ex, call);
                }
            }
            private void handleException(Exception ex, ServerCall<ReqT,RespT> call) {
                String msg = ex.getMessage() != null ? ex.getMessage() : ex.getClass().getSimpleName();

                Map<String, String> metadataMap = new HashMap<>();
                metadataMap.put("error-message", msg);

                com.google.rpc.Status.Builder rpcStatus = com.google.rpc.Status.newBuilder();

                if (ex instanceof GrpcException grpcEx) {
                    // Include code, params, language in metadata if present
                    if (grpcEx.getCode() != null) {
                        // write the enum key
                        metadataMap.put("error-code", grpcEx.getCode().getKey());
                    }
                    if (grpcEx.getLanguage() != null) {
                        metadataMap.put("error-lang", grpcEx.getLanguage());
                    }
                    if (grpcEx.getParams() != null && !grpcEx.getParams().isEmpty()) {
                        // prefix params with param- to keep them discoverable
                        grpcEx.getParams().forEach((k, v) -> metadataMap.put("param-" + k, v));
                    }

                    ErrorInfo errorInfo = ErrorInfo.newBuilder()
                            .setReason(ex.getClass().getSimpleName())
                            .setDomain("com.recipe.grpc")
                            .putAllMetadata(metadataMap)
                            .build();

                    rpcStatus.setCode(grpcEx.getStatus().getCode().value())
                            .setMessage(grpcEx.getMessage() != null ? grpcEx.getMessage() : msg)
                            .addDetails(Any.pack(errorInfo));
                } else {
                    ErrorInfo errorInfo = ErrorInfo.newBuilder()
                            .setReason(ex.getClass().getSimpleName())
                            .setDomain("com.recipe.grpc")
                            .putAllMetadata(metadataMap)
                            .build();

                    // Non-GrpcException -> map to INTERNAL and include details
                    rpcStatus.setCode(io.grpc.Status.INTERNAL.getCode().value())
                            .setMessage(msg)
                            .addDetails(Any.pack(errorInfo));
                }

                // Create a StatusException that contains the serialized grpc-status-details-bin trailers
                io.grpc.StatusException statusException = StatusProto.toStatusException(rpcStatus.build());

                // Use the trailers from the StatusException (they include grpc-status-details-bin),
                // and add our simple error-message entry as well.
                Metadata statusTrailers = statusException.getTrailers() != null ? statusException.getTrailers() : new Metadata();
                statusTrailers.put(Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER), msg);

                call.close(
                        statusException.getStatus(),
                        statusTrailers
                );
            }
        };
    }
}
