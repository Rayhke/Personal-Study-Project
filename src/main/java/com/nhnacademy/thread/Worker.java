package com.nhnacademy.thread;

/**
 * Thread 작업을 정의
 */
public class Worker implements Runnable {

    /**
     * Thread 를 start() 를 실행하면, run() 에 정의한 작업이 동작합니다.
     */
    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
                Execution.execution();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } while (Thread.currentThread().isAlive());
    }
}
