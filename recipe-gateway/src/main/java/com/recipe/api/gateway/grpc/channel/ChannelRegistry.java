package com.recipe.api.gateway.grpc.channel;


import io.grpc.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.client.GrpcChannelFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@AllArgsConstructor
public class ChannelRegistry {
    private final static Map<String , Channel> channels = new ConcurrentHashMap<>();
    private final GrpcChannelFactory grpcChannelFactory;

    public Channel getChannel(String name){

        log.info("Getting gRPC channel for name: {}", name);
        //            return ManagedChannelBuilder.forAddress("localhost", 50051).usePlaintext().build();
        return channels.computeIfAbsent(name, grpcChannelFactory::createChannel);
    }


}
