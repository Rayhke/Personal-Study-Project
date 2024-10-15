package com.nhnacademy.http.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public final class StringUtils {

    public static final String DEFAULT_CRLF = System.lineSeparator();

    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private StringUtils() {}

    public static boolean isNullOrEmpty(String s) {
        return Objects.isNull(s)
                || s.replace(" ", "").isBlank();
    }
}
