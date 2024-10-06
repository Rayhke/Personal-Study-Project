package com.nhnacademy.server.thread.pool;

import java.util.Objects;

public class WorkerThreadPool {

    private static final int DEFAULT_POOL_SIZE = 5;

    private final int poolSize;

    private final Thread[] workerThreads;

    private final Runnable runnable;

    public WorkerThreadPool(Runnable runnable) {
        this(DEFAULT_POOL_SIZE, runnable);
    }

    public WorkerThreadPool(int poolSize, Runnable runnable) {
        if (poolSize < 1) {
            throw new IllegalArgumentException("poolSize is range Out!");
        }
        if (Objects.isNull(runnable)) {
            throw new IllegalArgumentException("runnable is Null!");
        }

        this.poolSize = poolSize;
        this.runnable = runnable;

        this.workerThreads = new Thread[poolSize];
        initializePool();
    }

    private void initializePool() {
        for (int i = 0; i < poolSize; i++) {
            workerThreads[i] = new Thread(runnable);
        }
    }

    public synchronized void start() {
        // start()는 동기화 처리되어야 합니다.
        for (Thread thread : workerThreads) {
            thread.start();
        }
    }

    public synchronized void stop() {
        // stop() 동기화 처리되어야 합니다.
        for (Thread thread : workerThreads) {
            if (Objects.isNull(thread) || thread.isInterrupted()) { continue; }
            thread.interrupt();
        }

        for (Thread thread : workerThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
