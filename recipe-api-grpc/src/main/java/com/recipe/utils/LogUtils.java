package com.recipe.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class LogUtils {


    public static void logRequest(String method, Object request) {
        log.info("gRPC Request  | method={} | payload={}",
                method, request);
    }

    public static void logResponse(String method, Object response, long timeMs) {
        log.info("gRPC Response | method={} | time={}ms | payload={}",
                method, timeMs, response);
    }

    public static void logError(String method, Throwable t) {
        log.error("gRPC Error | method={}", method, t);
    }

}
