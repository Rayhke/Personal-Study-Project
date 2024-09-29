package com.nhnacademy.game.error;

/**
 * Bounds 이 제한된 영역을 벗어날 경우
 */
public class OutOfBoundsException extends RuntimeException {

    private static final String MESSAGE = "도형의 영역이 정수 공간을 벗어 납니다.";

    public OutOfBoundsException() {
        super(MESSAGE);
    }
}
