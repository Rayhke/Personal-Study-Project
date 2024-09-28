package com.nhnacademy.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Thread 리스트 모음
 */
public class ThreadPool {

    /**
     * P.K Key 목적으로 사용
     */
    private static final AtomicInteger id = new AtomicInteger(0);

    /**
     * Default Size
     */
    private static final int DEFAULT_POOL_SIZE = 10;

    /**
     * Thread List 의 Size
     */
    private final int poolSize;

    /**
     * Thread 가 수행할 작업
     */
    private final Runnable runnable;

    /**
     * Thread 를 관리하는 List
     */
    private final List<Thread> threadList;

    /**
     * Default Constructor
     * @param runnable Thread 가 수행할 작업
     */
    public ThreadPool(Runnable runnable) {
        this(DEFAULT_POOL_SIZE, runnable);
    }

    /**
     * Main Constructor
     *
     * @param poolSize ThreadPool 의 최대 사이즈
     * @param runnable Thread 가 수행할 작업
     * @throws IllegalArgumentException <br>
     * 1. poolSize 가 1미만일 경우<br>
     * 2. runnable 이 null 일 경우
     */
    public ThreadPool (int poolSize, Runnable runnable) {
        if (poolSize < 0) {
            throw new IllegalArgumentException();
        }

        if (Objects.isNull(runnable)) {
            throw new IllegalArgumentException();
        }

        this.poolSize = poolSize;
        this.runnable = runnable;
        this.threadList = new ArrayList<>(poolSize);

        createThread();
    }

    /**
     * Thread List 안의 Thread 들에게 작업을 할당
     */
    private void createThread() {
        synchronized (this) {
            while (threadList.size() < poolSize) {
                Thread thread = new Thread(runnable);
                thread.setName(String.format("일꾼%d", id.incrementAndGet()));
                threadList.add(thread);
            }
        }
    }

    /**
     * Thread List 안의 Thread 들에게 작업 시작을 명령
     */
    public synchronized void start() {
        for (int i = 0; i < poolSize; i++) {
            threadList.get(i).start();
        }
    }

    /**
     * Thread List 안의 Thread 들에게 작업 중단을 명령
     */
    public synchronized void stop() {
        for (Thread thread : threadList) {
            if (Objects.isNull(thread) || !thread.isAlive()) {
                continue;
            }
            thread.interrupt();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
