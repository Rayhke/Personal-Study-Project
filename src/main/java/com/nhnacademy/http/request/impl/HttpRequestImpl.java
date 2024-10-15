package com.nhnacademy.http.request.impl;

import com.nhnacademy.http.request.HttpRequest;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

public class HttpRequestImpl implements HttpRequest {

    private static final String KEY_HTTP_METHOD = "HTTP-METHOD";

    private static final String KEY_QUERY_PARAM_MAP = "HTTP-QUERY-PARAM-MAP";

    private static final String KEY_REQUEST_PATH = "HTTP-REQUEST-PATH";

    private static final String KEY_CONTENT_TYPE = "Content-Type";

    private static final String KEY_CONTENT_LENGTH = "Content-Length";

    private static final String KEY_HOST = "Host";

    private static final String HEADER_DELIMITER = ":";

    private final Map<String, Object> headerMap;

    private final Map<String, Object> attributeMap;

    private final Socket client;

    private boolean isBodyRequest;

    public HttpRequestImpl(Socket client) {
        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("client socket is Null!");
        }
        this.client = client;
        this.headerMap = new HashMap<>();
        this.attributeMap = new HashMap<>();
        this.initialize();
    }

    private void initialize() {

    }

    // =================================================================================================================
    // method

    @Override
    public Object getAttribute(String name) {
        return Optional.ofNullable(attributeMap.get(name))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public void setAttribute(String name, Object object) {

    }

    @Override
    public Map<String, String> getParameterMap() {
        return Stream.of(headerMap.get(KEY_QUERY_PARAM_MAP))
                        .filter(Map.class::isInstance)
                        .map(o -> (Map<String, String>) o)
                        .findFirst()
                        .orElse(new HashMap<>());
    }

    @Override
    public String getParameter(String name) {
        return Optional.ofNullable(getParameterMap().get(name))
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public String getHost() {
        return "";
    }

    @Override
    public String getMethod() {
        return "";
    }

    @Override
    public String getRequestURI() {
        return "";
    }

    @Override
    public String getHeader(String name) {
        return "";
    }
}
