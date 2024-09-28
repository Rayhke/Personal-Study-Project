package com.nhnacademy.util;

/**
 * 카운트를 관리하는 Class
 */
public class Counter {

    /**
     * 기본 카운트 최대값
     */
    private static final int DEFAULT_MAX_COUNT = 10;

    /**
     * 카운트 최대값
     */
    private int maxCount;

    /**
     * 현재 카운트
     */
    private int nowCount;

    /**
     * 카운트 최대값을 임의로 지정하지 않을 시, 기본값으로 세팅됩니다.
     */
    public Counter() {
        this(DEFAULT_MAX_COUNT);
    }

    /**
     *
     * @param maxCount 카운트 최대값
     * @exception IllegalArgumentException 0 이하의 값을 입력 받았을 시, 동작한다.
     */
    public Counter(int maxCount) {
        if (maxCount <= 0) {
            throw new IllegalArgumentException("1 이상의 값을 입력해주세요.");
        }
        this.maxCount = maxCount;
        this.nowCount = 0;
    }

    /**
     * 카운트 최대값을 반환한다.
     * @return 카운트 최대값
     */
    public int getMaxCount() {
        return maxCount;
    }

    /**
     * 현재 카운트를 반환한다.
     * @return 현재 카운트
     */
    public int getNowCount() {
        return nowCount;
    }

    /**
     * 현재 카운트에 1을 더한다.
     */
    public void addNowCount() {
        nowCount++;
    }
}
