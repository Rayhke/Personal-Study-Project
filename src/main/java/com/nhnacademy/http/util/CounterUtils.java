package com.nhnacademy.http.util;

import com.nhnacademy.http.context.ContextHolder;

import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public final class CounterUtils {

    public static final String CONTENT_COUNTER_NAME = "Global-Counter";

    private CounterUtils() {}

    // TODO : 검증할 것
    static {
        ContextHolder.getApplicationContext()
                        .setAttribute(CONTENT_COUNTER_NAME, new AtomicLong());
    }

    public static synchronized long increaseAndGet() {
        return Stream.of(ContextHolder.getApplicationContext()
                                        .getAttribute(CONTENT_COUNTER_NAME))
                .filter(AtomicLong.class::isInstance)
                .map(AtomicLong.class::cast)
                .findFirst()
                .map(AtomicLong::incrementAndGet)
                .orElseThrow(IllegalAccessError::new);
    }
}
