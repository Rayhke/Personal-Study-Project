package com.nhnacademy.util;

import java.util.Objects;

/**
 * {@link Runnable}을 활용해서 구현
 */
public class CounterRunnable implements Runnable {

    private static final int DEFAULT_MAX_COUNT = 10;

    private final Counter counter;

    public CounterRunnable() {
        this(DEFAULT_MAX_COUNT);
    }

    public CounterRunnable(int maxCount) {
        this.counter = new Counter(maxCount);
    }

    public CounterRunnable(Counter counter) {
        if (Objects.isNull(counter)) {
            throw new IllegalArgumentException("Counter cannot be null");
        }
        this.counter = counter;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
                counter.addNowCount();
                System.out.println("Runnable Counter : " + counter.getNowCount());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new IllegalArgumentException(e);
            }
        } while (counter.getNowCount() < counter.getMaxCount());
    }
}
