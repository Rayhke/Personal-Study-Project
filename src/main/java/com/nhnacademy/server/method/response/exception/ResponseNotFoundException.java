package com.nhnacademy.server.method.response.exception;

public class ResponseNotFoundException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 Response 입니다.";

    public ResponseNotFoundException(String method) {
        super(String.format("%s : %s", MESSAGE, method));
    }
}
