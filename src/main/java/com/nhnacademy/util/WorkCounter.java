package com.nhnacademy.util;

/**
 * 일꾼 전체가 수행한 작업량을 관리하는 class
 */
public final class WorkCounter {

    /**
     * 모든 일꾼이 수행한 작업량 (합계)
     */
    private static int workload = 0;

    /**
     * 일꾼 한명이 수행한 작업량을 추가합니다.
     * @param working 일꾼 한명의 작업량
     */
    public static void addWorkload(int working) {
        workload += working;
    }

    /**
     * 전체 작업량을 반환합니다.
     * @return 전체 작업량
     */
    public static int getWorkload() {
        return workload;
    }
}
