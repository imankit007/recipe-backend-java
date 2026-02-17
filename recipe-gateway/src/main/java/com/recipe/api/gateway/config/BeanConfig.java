package com.recipe.api.gateway.config;

import com.recipe.api.gateway.grpc.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;

public class BeanConfig {


    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }

}
