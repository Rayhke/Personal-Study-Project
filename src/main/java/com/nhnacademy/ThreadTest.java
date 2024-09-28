package com.nhnacademy;

import org.example02.cord.util.CounterThread;

public class ThreadTest {

    public static void main(String[] args) {
        CounterThread counterThread = new CounterThread();
        counterThread.start();
    }
}
