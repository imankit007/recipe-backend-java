package com.recipe.auth.grpc.interceptor;

import io.grpc.*;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;
import com.recipe.core.utils.LogUtils;

@Component
@GlobalServerInterceptor
public class GrpcLoggingInterceptor implements ServerInterceptor {
    @Override
    public <I, O> ServerCall.Listener<I> interceptCall(ServerCall<I, O> call, Metadata headers, ServerCallHandler<I, O> next) {


        String methodName = call.getMethodDescriptor().getFullMethodName();
        long startTime = System.currentTimeMillis();

        ServerCall.Listener<I> listener = next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<I, O>(call) {
                    @Override
                    public void sendMessage(O message) {
                        long time = System.currentTimeMillis() - startTime;
                        LogUtils.logResponse(methodName, message, time);
                        super.sendMessage(message);
                    }

                    @Override
                    public void close(Status status, Metadata trailers) {
                        if (!status.isOk()) {
                            LogUtils.logError(methodName, status.asRuntimeException());
                        }
                        super.close(status, trailers);
                    }
                }, headers);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
            @Override
            public void onMessage(I message) {
                LogUtils.logRequest(methodName, message);
                super.onMessage(message);
            }
        };
    }
}
