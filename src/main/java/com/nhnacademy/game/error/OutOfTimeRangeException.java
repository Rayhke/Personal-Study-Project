package com.nhnacademy.game.error;

public class OutOfTimeRangeException extends RuntimeException {

    private static final String MESSAGE = "입력된 값이 ms 범위를 벗어납니다.";

    public OutOfTimeRangeException(int dt) {
        super(String.format("%s : %d", MESSAGE, dt));
    }
}
