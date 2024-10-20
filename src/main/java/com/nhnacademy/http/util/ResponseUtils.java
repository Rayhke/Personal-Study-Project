package com.nhnacademy.http.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;

@Slf4j
public final class ResponseUtils {

    public enum HttpStatus {
        OK(200, "OK"),
        REDIRECT(301, "Moved Permanently"),
        NOT_FOUND(404, "Not Found"),
        METHOD_NOT_FOUND(405, "Method Not Allowed"),
        UNKNOWN(-1, "Unknown Status");

        private final int code;

        private final String description;

        HttpStatus(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public int getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

        public static HttpStatus getStatusFromCode(int code) {
            for (HttpStatus status : HttpStatus.values()) {
                if (status.getCode() == code) {
                    return status;
                }
            }
            return UNKNOWN;
        }
    }

    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    public static final String DEFAULT_404 = "/404.html";

    public static final String DEFAULT_405 = "/405.html";

    private ResponseUtils() {}

    public static boolean isExist(String filePath) {
        URL url = ResponseUtils.class.getResource(filePath);
        return Objects.nonNull(url);
    }

    public static String tryGetBodyFromFile(String filePath) {
        StringBuilder responseBody = new StringBuilder();
        try (InputStream inputStream = ResponseUtils.class.getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, DEFAULT_CHARSET_NAME))
        ) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                responseBody.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return responseBody.toString();
    }

    public static String createResponseHeader(int httpStatusCode, Charset charset, int contentLength) {
        StringBuilder responseHeader = new StringBuilder();
        responseHeader.append(String.format("HTTP/1.0 %d %s%s",
                                httpStatusCode,
                                HttpStatus.getStatusFromCode(httpStatusCode).getDescription(),
                                StringUtils.DEFAULT_CRLF));
        responseHeader.append(String.format("Server: HTTP server/0.1%s",
                                StringUtils.DEFAULT_CRLF));
        responseHeader.append(String.format("Content-type: text/html; charset=%s%s",
                                charset,
                                StringUtils.DEFAULT_CRLF));
        responseHeader.append(String.format("Connection: Closed%s",
                                StringUtils.DEFAULT_CRLF));
        responseHeader.append(String.format("Content-Length: %d %s%s",
                                contentLength,
                                StringUtils.DEFAULT_CRLF,
                                StringUtils.DEFAULT_CRLF));

        return responseHeader.toString();
    }
}
