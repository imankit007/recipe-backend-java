package com.recipe.api.gateway.grpc.client;

import io.grpc.stub.AbstractStub;

public abstract class AbstractGrpcClient {

    protected int maxInboundMessageSize = 4 * 1024 * 1024; // 4 MB
    protected int maxOutboundMessageSize = 4 * 1024 * 1024;

    @SuppressWarnings("rawtypes")
    protected <T extends AbstractStub> T initialize(final T stub) {
        return initialize(stub, maxInboundMessageSize, maxOutboundMessageSize);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    protected <T extends AbstractStub> T initialize(final T stub, final int maxInboundMessageSize, final int maxOutboundMessageSize) {
        return (T) stub.withMaxInboundMessageSize(maxInboundMessageSize)
                .withMaxOutboundMessageSize(maxOutboundMessageSize);
    }

}
