package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.util.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeResponse implements Response {

    private static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    @Override
    public String getMethod() {
        return "time";
    }

    @Override
    public String execute(String value) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter;

        if (StringUtils.isNullOrEmpty(value)) {
            formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
        } else {
            try {
                formatter = DateTimeFormatter.ofPattern(value);
            } catch (Exception e) {
                // pattern 양식이 올바르지 않을 경우, 다시 Default 값으로 rollback
                formatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
            }
        }

        return now.format(formatter);
    }
}
