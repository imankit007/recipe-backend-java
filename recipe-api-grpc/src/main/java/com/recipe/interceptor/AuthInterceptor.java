package com.recipe.interceptor;

import com.recipe.core.utils.JwtUtil;
import io.grpc.*;
import lombok.RequiredArgsConstructor;
import org.springframework.grpc.server.GlobalServerInterceptor;


@GlobalServerInterceptor
@RequiredArgsConstructor
public class AuthInterceptor implements ServerInterceptor {

    private final JwtUtil jwtUtil;
    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        String token = headers.get(Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER));
        if (token == null || !jwtUtil.validateToken(token)) {
            call.close(Status.UNAUTHENTICATED, headers);
            return new ServerCall.Listener<>() {};
        }
        return next.startCall(call, headers);

    }
}
