package com.nhnacademy.game.error;

public class InvalidSizeException extends RuntimeException {

    private static final String MESSAGE = "반지름의 크기는 0보다 커야 합니다.";

    public InvalidSizeException(int width, int height) {
        super(String.format("%s -> [%s : %d]", MESSAGE, (width < 1) ? "width" : "height", (width < 1) ? width : height));
    }
}
