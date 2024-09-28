package com.nhnacademy;

import org.example02.cord.util.Counter;

public class MainTest {

    public static void main(String[] args) {
        Counter counter = new Counter();

        do {
            try {
                Thread.sleep(1000);
                counter.addNowCount();
                System.out.println("Main Counter : " + counter.getNowCount());
            } catch (InterruptedException e) {
                throw new IllegalArgumentException(e);
            }
        } while (counter.getNowCount() < counter.getMaxCount());
    }
}
