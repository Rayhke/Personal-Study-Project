package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.util.StringUtils;

public class EchoResponse implements Response {

    // echo method 에 해당되는 응답을 구현 합니다.
    private static final String METHOD = "echo";

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String execute(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("value is Null!");
        }
        return value;
    }
}