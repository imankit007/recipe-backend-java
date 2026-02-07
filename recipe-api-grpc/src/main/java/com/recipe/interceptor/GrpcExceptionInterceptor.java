package com.recipe.interceptor;

import com.recipe.core.exception.GrpcException;
import io.grpc.*;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

@Component
@GlobalServerInterceptor
public class GrpcExceptionInterceptor implements ServerInterceptor {

    private static final Metadata.Key<String> ERROR_MESSAGE_KEY =
            Metadata.Key.of("error-message", Metadata.ASCII_STRING_MARSHALLER);

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

            @Override
            public void onReady() {
                try {
                    super.onReady();
                } catch (Exception ex) {
                    handleException(ex, call);
                }
            }

            private void handleException(Exception ex, ServerCall<ReqT,RespT> call) {

                Status status;

                if (ex instanceof GrpcException grpcEx) {
                    status = grpcEx.getStatus();
                } else {
                    status = Status.INTERNAL;
                }

                Metadata trailers = new Metadata();
                trailers.put(ERROR_MESSAGE_KEY, ex.getMessage());

                call.close(
                        status.withDescription(ex.getMessage())
                                .withCause(ex),
                        trailers
                );
            }
        };
    }
}
