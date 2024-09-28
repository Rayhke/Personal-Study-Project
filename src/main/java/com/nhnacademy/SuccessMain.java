package com.nhnacademy;

import com.nhnacademy.thread.ThreadPool;
import com.nhnacademy.thread.Worker;

public class SuccessMain {

    public static void main(String[] args) {
        Worker worker = new Worker();
        ThreadPool threadPool = new ThreadPool(worker);
        threadPool.start();

        // 20초 가량 Main Thread 를 중지
        try {
            Thread.sleep(20 * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        threadPool.stop();
    }
}
