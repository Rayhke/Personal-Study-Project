package com.nhnacademy.server.method.parser;

import com.nhnacademy.util.StringUtils;

import java.util.Objects;

public class MethodParser {

    public static MethodAndValue parse(String message) {
        if (StringUtils.isNullOrEmpty(message)) {
            return null; // Optional and Stream 구조도 나쁘진 않겠다..
        }

        String[] data = message.split(" ");
        String method;
        String value;

        // 데이터는 정상이 맞는 데, 양식이 틀어져서 띄어쓰기가 없는 경우
        if (data.length < 2) {
            method = data[0];
            return new MethodAndValue(method, "");
        }

        method = data[0];
        value = data[1];
        return new MethodAndValue(method, value);
    }

    public static class MethodAndValue {

        private final String method;

        private final String value;

        public MethodAndValue(String method, String value) {
            if (StringUtils.isNullOrEmpty(method)) {
                throw new IllegalArgumentException("method is Null!");
            }

            if (Objects.isNull(value)) {
                throw new IllegalArgumentException("value is Null!");
            }

            this.method = method;
            this.value = value;
        }

        public String getMethod() {
            return method;
        }

        public String getValue() {
            return value;
        }
    }
}
