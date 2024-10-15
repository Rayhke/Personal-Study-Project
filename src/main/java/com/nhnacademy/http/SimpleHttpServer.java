package com.nhnacademy.http;

import com.nhnacademy.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleHttpServer {

    private static final int DEFAULT_PORT = 8080;

    private final int port;

    private final RequestChannel requestChannel;

    private final WorkerThreadPool workerThreadPool;

    public SimpleHttpServer() {
        this(DEFAULT_PORT);
    }

    public SimpleHttpServer(int port) {
        if (port < 0 || 65535 < port) {
            throw new IllegalArgumentException(String.format("port is range Out! : %d", port));
        }
        this.port = port;
        this.requestChannel = new RequestChannel();
        this.workerThreadPool = new WorkerThreadPool(requestChannel);
    }
}
