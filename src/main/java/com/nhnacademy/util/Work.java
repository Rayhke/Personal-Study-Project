package com.nhnacademy.util;

import java.util.Random;

/**
 * 작업 util
 */
public class Work {

    private static final Random random = new Random();

    /**
     * 일꾼이 한번의 작업 수행의 결과가 1 ~ 5 사이의 값이 할당 됩니다.
     * @return 일꾼이 수행한 작업량
     */
    public static int getWorking() {
        return random.nextInt(5) + 1;
    }
}
