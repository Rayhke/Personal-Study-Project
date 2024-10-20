package com.nhnacademy.http;

import com.nhnacademy.http.channel.RequestChannel;
import com.nhnacademy.http.channel.impl.HttpJob;
import com.nhnacademy.http.context.Context;
import com.nhnacademy.http.context.ContextHolder;
import com.nhnacademy.http.service.impl.IndexHttpService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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

        Context context = ContextHolder.getApplicationContext();
        context.setAttribute(IndexHttpService.URI, new IndexHttpService());
    }

    // =================================================================================================================
    // method

    public synchronized void start() throws IOException {
        workerThreadPool.start();

        try (ServerSocket serverSocket = new ServerSocket(port)
        ) {
            while (!serverSocket.isClosed()) {
                Socket client = serverSocket.accept();
                requestChannel.addHttpJob(new HttpJob(client));
            }
        } catch (Exception e) {
            log.error("server error : {}", e.getMessage(), e);
        }
    }
}
