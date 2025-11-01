package com.recipe.core.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.netty.shaded.io.grpc.netty.NettyServerBuilder;
import io.grpc.protobuf.services.ProtoReflectionService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.AccessLevel;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


@Component
@Slf4j
@Setter
public class GrpcServer {

    private static final String GRPC_PORT = "grpc.server.port";
    private static final String GRPC_ADDRESS = "grpc.server.address";
    private static final int DEFAULT_GRPC_PORT = 50051;
    private static final String DEFAULT_GRPC_ADDRESS = "127.0.0.1";

    private final AtomicReference<ServerStatus> status = new AtomicReference<>(ServerStatus.STOPPED);
    private int port;
    private String address;

    @Autowired(required = false)
    private List<BindableService> services;

    @Autowired(required = false)
    private Environment env;

    @Setter(AccessLevel.NONE)
    private Server server;

    @PostConstruct
    public void start() {
        parseGrpcConfig();
        buildServer();
        startServer();
    }

    @PreDestroy
    public void stop(){
        if(server == null || !status.compareAndSet(ServerStatus.READY, ServerStatus.STOPPING)){
            log.warn("gRPC server is not running.");
            return;
        }
        server.shutdown();

        try{
            if(!server.awaitTermination(1000L, TimeUnit.SECONDS)){
                log.warn("gRPC server did not shut down in the allocated time, forcing shutdown...");
                server.shutdownNow();
                server.awaitTermination(5, TimeUnit.SECONDS);
            }
        }catch (InterruptedException ex){
            server.shutdownNow();
        }
    }

    protected void parseGrpcConfig() {
        log.info("parsing grpc address/port from environment or system properties...");

        address = "0.0.0.0";
        port = 50051;

        return;

//        // Address: prefer system property, then spring property, then default
//        String addr = System.getProperty(GRPC_ADDRESS);
//        if (addr == null && env != null) {
//            addr = env.getProperty("spring.grpc.server.address");
//            if (addr == null) {
//                addr = env.getProperty(GRPC_ADDRESS);
//            }
//        }
//        if (addr == null) {
//            address = DEFAULT_GRPC_ADDRESS;
//            log.info("no grpc address specified, using default: {}", DEFAULT_GRPC_ADDRESS);
//        } else {
//            address = addr;
//            log.info("grpc address set to: {}", address);
//        }
//
//        // Port: prefer system property, then spring property, then default
//        String portStr = System.getProperty(GRPC_PORT);
//        if (portStr == null && env != null) {
//            portStr = env.getProperty("spring.grpc.server.port");
//            if (portStr == null) {
//                portStr = env.getProperty(GRPC_PORT);
//            }
//        }
//        if (portStr == null) {
//            port = DEFAULT_GRPC_PORT;
//            log.info("no grpc port specified, using default: {}", DEFAULT_GRPC_PORT);
//            return;
//        }
//        try {
//            port = Integer.parseInt(portStr);
//            log.info("grpc port set to: {}", port);
//        } catch (NumberFormatException e) {
//            port = DEFAULT_GRPC_PORT;
//            log.warn("invalid grpc port specified: {}, using default: {}", portStr, DEFAULT_GRPC_PORT);
//        }
    }

    protected void buildServer(){
        InetSocketAddress bindAddress = new InetSocketAddress(address, port);
        NettyServerBuilder serverBuilder = NettyServerBuilder.forAddress(bindAddress);
        if (services != null) {
            for (BindableService service : services) {
                log.info("Registering gRPC service: {}", service.getClass().getName());
                serverBuilder.addService(service);
            }
        }
        serverBuilder.addService(ProtoReflectionService.newInstance());
        server = serverBuilder.build();
    }

    protected void startServer()  {
        // Only set STARTING if currently STOPPED
        if (!status.compareAndSet(ServerStatus.STOPPED, ServerStatus.STARTING)) {
            log.warn("gRPC server is already running or starting.");
            return;
        }
        try{
            server.start();
        }catch (IOException e){
            log.error("Failed to start gRPC server", e);
            status.set(ServerStatus.STOPPED);
        }

        status.set(ServerStatus.READY);
        log.info("gRPC server started, listening on {}:{}", address, port);
        Runtime.getRuntime().addShutdownHook(new Thread(GrpcServer.this::stop));
        new Thread(() ->{
           try{
               server.awaitTermination();
               log.info("Server has been terminated.");
           } catch (InterruptedException ex){
               status.set(ServerStatus.STOPPED);
           }
        }, "grpc-server-daemon").start();
    }

    public enum ServerStatus {
        STARTING,
        READY,
        STOPPING,
        STOPPED
    }
}