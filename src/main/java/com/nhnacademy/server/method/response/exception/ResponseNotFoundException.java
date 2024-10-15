package com.nhnacademy.server.method.response.exception;

public class ResponseNotFoundException extends RuntimeException {

    public ResponseNotFoundException(String method) {
        super(String.format("존재하지 않는 Response 입니다. : %s", method));
    }
}
