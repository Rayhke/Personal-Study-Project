package com.nhnacademy.game.error;

import java.util.UUID;

public class AlreadyExistException extends RuntimeException {

    private static final String MESSAGE = "World 내에 이미 존재하는 도형입니다.";

    public AlreadyExistException(UUID id) {
        super(String.format("%s : %s", MESSAGE, id));
    }
}
