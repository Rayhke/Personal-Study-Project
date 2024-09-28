package com.nhnacademy;

import org.example02.cord.util.CounterRunnable;

public class RunnableTest {

    public static void main(String[] args) {
        CounterRunnable counterRunnable = new CounterRunnable();
        Thread thread = new Thread(counterRunnable);
        thread.start();
    }
}
