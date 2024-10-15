package com.nhnacademy.http;

import com.nhnacademy.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class WorkerThreadPool {

    private static final int DEFAULT_POOL_SIZE = 5;

    private final int poolSize;

    private final Thread[] workerThreads;

    private final RequestChannel requestChannel;

    public WorkerThreadPool(RequestChannel requestChannel) {
        this(DEFAULT_POOL_SIZE, requestChannel);
    }

    public WorkerThreadPool(int poolSize, RequestChannel requestChannel) {
        if (poolSize < 1) {
            throw new IllegalArgumentException(
                    String.format("poolSize is range Out! : %d", poolSize)
            );
        }
        if (Objects.isNull(requestChannel)) {
            throw new IllegalArgumentException("requestChannel is Null!");
        }

        this.poolSize = poolSize;
        this.requestChannel = requestChannel;

        HttpRequestHandler httpRequestHandler = new HttpRequestHandler(this.requestChannel);

        int index = 0;
        this.workerThreads = new Thread[poolSize];
        for (Thread thread : workerThreads) {
            thread = new Thread(httpRequestHandler);
            thread.setName(String.format("thread-%d", ++index));
        }
    }

    public synchronized void start() {
        for (Thread thread : workerThreads) {
            thread.start();
        }
    }

    public synchronized void stop() {
        for (Thread thread : workerThreads) {
            if (Objects.isNull(thread)
                    || !thread.isAlive()) { continue; }
            thread.interrupt();
        }

        for (Thread thread : workerThreads) {
            try {
                if (Objects.isNull(thread)
                        || !thread.isAlive()) { continue; }
                thread.join();
            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }
}
