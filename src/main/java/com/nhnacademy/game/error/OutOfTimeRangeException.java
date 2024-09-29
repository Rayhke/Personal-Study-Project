package com.nhnacademy.game.error;

public class OutOfTimeRangeException extends RuntimeException {

    // TODO : 메세지 설정하고 싶은 거 떠올리기
    private static final String MESSAGE = "지정된";

    public OutOfTimeRangeException(int dt) {
        super(String.format("%s : %d", MESSAGE, dt));
    }
}
