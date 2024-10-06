package com.nhnacademy.server.thread.channel;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class RequestChannel {

    private static final long QUEUE_MAX_SIZE = 10;

    private final Queue<Executable> requestQueue;

    private final long queueSize;

    public RequestChannel() {
        this(QUEUE_MAX_SIZE);
    }

    public RequestChannel(long queueSize) {
        if (queueSize < 0) {
            throw new IllegalArgumentException("maxSize is range Out!");
        }
        this.requestQueue = new LinkedList<>();
        this.queueSize = queueSize;
    }

    public synchronized void addJob(Executable executable) {
        if (Objects.isNull(executable)) {
            throw new IllegalArgumentException("executable is Null!");
        }
        while (queueSize <= requestQueue.size()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        requestQueue.add(executable);
        notify();
    }

    public synchronized Executable getJob() {
        while (requestQueue.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        notify();
        return requestQueue.poll();
    }
}
