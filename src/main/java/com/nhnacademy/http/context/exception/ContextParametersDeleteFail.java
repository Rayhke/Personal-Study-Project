package com.nhnacademy.http.context.exception;

public class ContextParametersDeleteFail extends RuntimeException {

    public ContextParametersDeleteFail(String name) {
        super(String.format("Object Delete Fila : %s", name));
    }
}
