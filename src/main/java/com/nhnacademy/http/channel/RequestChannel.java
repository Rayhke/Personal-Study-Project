package com.nhnacademy.http.channel;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class RequestChannel {

    private static final long MAX_QUEUE_SIZE = 10L;

    private final long queueSize;

    private final Queue<Executable> requestQueue;

    public RequestChannel() {
        this(MAX_QUEUE_SIZE);
    }

    public RequestChannel(long queueSize) {
        if (queueSize < 0L) {
            throw new IllegalArgumentException(
                    String.format("queueSize is range Out! : %d", queueSize)
            );
        }
        this.queueSize = queueSize;
        this.requestQueue = new LinkedList<>();
    }

    // =================================================================================================================
    // method

    public synchronized void addHttpJob(Executable executable) {
        while (queueSize <= requestQueue.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        try {
            requestQueue.add(executable);
        } finally {
            notifyAll();
        }
    }

    public synchronized Executable getHttpJob() {
        while (requestQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error("{}", e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }

        try {
            return requestQueue.poll();
        } finally {
            notifyAll();
        }
    }
}
