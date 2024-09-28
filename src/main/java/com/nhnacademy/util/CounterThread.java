package com.nhnacademy.util;

import java.util.Objects;

/**
 * {@link Thread}를 활용해서 구현
 */
public class CounterThread extends Thread {

    private static final int DEFAULT_MAX_COUNT = 10;

    private final Counter counter;

    public CounterThread() {
        this(DEFAULT_MAX_COUNT);
    }

    public CounterThread(int maxCount) {
        this.counter = new Counter(maxCount);
    }

    public CounterThread(Counter counter) {
        if (Objects.isNull(counter)) {
            throw new IllegalArgumentException("Counter cannot be null");
        }
        this.counter = counter;
    }

    @Override
    public void run() {
        do {
            try {
                sleep(1000);
                counter.addNowCount();
                System.out.println("Thread Counter : " + counter.getNowCount());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalArgumentException(e);
            }
        } while (counter.getNowCount() < counter.getMaxCount());
    }
}
