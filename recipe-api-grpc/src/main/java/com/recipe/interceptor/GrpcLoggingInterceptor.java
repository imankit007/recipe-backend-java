package com.recipe.interceptor;

import com.recipe.core.utils.LogUtils;
import io.grpc.*;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;


@Component
@GlobalServerInterceptor
public class GrpcLoggingInterceptor implements ServerInterceptor {
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {


        String methodName = call.getMethodDescriptor().getFullMethodName();
        long startTime = System.currentTimeMillis();

        ServerCall.Listener<ReqT> listener = next.startCall(
                new ForwardingServerCall.SimpleForwardingServerCall<>(call) {
                    @Override
                    public void sendMessage(RespT message) {
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
            public void onMessage(ReqT message) {
                LogUtils.logRequest(methodName, message);
                super.onMessage(message);
            }
        };
    }
}
