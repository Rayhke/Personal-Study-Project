package com.nhnacademy.game.error;

public class ShapeNotFoundException extends RuntimeException {
    
    private static final String MESSAGE = "존재하지 않는 도형입니다.";

    public ShapeNotFoundException() {
        super(MESSAGE);
    }
}
