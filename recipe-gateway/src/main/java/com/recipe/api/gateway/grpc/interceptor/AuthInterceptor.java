package com.recipe.api.gateway.grpc.interceptor;


import com.recipe.core.utils.JwtUtil;
import io.grpc.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;


@RequiredArgsConstructor
@Component
public class AuthInterceptor implements ClientInterceptor {

    private JwtUtil jwtUtil;

    private static final List<String> PUBLIC_METHODS =
            List.of("AuthService/Login", "AuthService/Register");


    @Override
    public <ReqT, RespT> ClientCall<ReqT, RespT> interceptCall(MethodDescriptor<ReqT, RespT> method, CallOptions callOptions, Channel next) {
        return new ForwardingClientCall.SimpleForwardingClientCall<>(next.newCall(method, callOptions)) {
            @Override
            public void start(Listener<RespT> responseListener, Metadata headers) {
                ServletRequestAttributes attributes =
                        (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

                if (attributes != null) {
                    HttpServletRequest request = attributes.getRequest();

                    String authHeader = request.getHeader("Authorization");
                    ;

                    if (authHeader != null) {
                        String token = authHeader.startsWith("Bearer ") ? authHeader.substring(7) : authHeader;
                        Metadata.Key<String> authKey =
                                Metadata.Key.of("Authorization",
                                        Metadata.ASCII_STRING_MARSHALLER);

                        // 👇 Set HTTP header as gRPC metadata
                        headers.put(authKey, token);
                    }
                }



                super.start(responseListener, headers);
            }
        };
    }
}