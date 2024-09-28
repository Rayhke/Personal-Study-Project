package com.nhnacademy;

import com.nhnacademy.thread.ThreadPool;
import com.nhnacademy.thread.WorkerFail;

public class FailMain {

    public static void main(String[] args) {
        WorkerFail workerFail = new WorkerFail();
        ThreadPool threadPool = new ThreadPool(workerFail);
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
