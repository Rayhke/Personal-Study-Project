package com.nhnacademy.thread;

import com.nhnacademy.util.Work;
import com.nhnacademy.util.WorkCounter;

/**
 * 작업 실행
 */
public class Execution {

    public static synchronized void execution() {
        try {
            Thread.sleep(1000);
            int working = Work.getWorking();
            WorkCounter.addWorkload(working);
            System.out.printf("[%s]가 %d 만큼 작업을 수행\t\t| 전체 작업량 합계 = %d%n", Thread.currentThread().getName(), working, WorkCounter.getWorkload());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
