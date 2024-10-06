package com.nhnacademy.server.method.response;

import com.nhnacademy.util.StringUtils;

public interface Response {

    String getMethod();

    String execute(String value);

    default boolean validate(String method) {
        if (StringUtils.isNullOrEmpty(method)) {
            throw new IllegalArgumentException("method is Null!");
        }
        return getMethod().equals(method);
    }
}
