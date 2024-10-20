package com.nhnacademy.http.request.impl;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
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
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
            isBodyRequest = false;

            log.debug("------HTTP-REQUEST_start()");
            // =========================================================================
            // Header Request : First Line
            firstLineParser(bufferedReader.readLine());
            // =========================================================================
            // Header Request : Middle Line
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (StringUtils.isNullOrEmpty(line)) { break; }
                headerParser(line);
            }
            // =========================================================================
            // Body Request : Line & Content-Length
            if (isBodyRequest) {
                String rawContentLength = getContentLength();
                int contentLength = Integer.parseInt(rawContentLength);

                char[] body = new char[contentLength];
                bufferedReader.read(body);

                parametersParser(new String(body).split("&"));
            }
            // =========================================================================
            log.debug("------HTTP-Request_end()");
        } catch (IOException e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private void headerParser(String line) {
        log.debug("{}", line);

        int firstValueIndex = line.indexOf(HEADER_DELIMITER);
        int lastValueIndex = line.length();

        if (firstValueIndex == -1) { return; }
        String key = line.substring(0, firstValueIndex++).trim();
        if (line.contains(KEY_CONTENT_TYPE)
                && (line.contains("; charset") || line.contains(";charset"))) {
            lastValueIndex = line.lastIndexOf(";") + 1;
            String[] charset = line.substring(lastValueIndex).split("=");
            headerMap.put(charset[0].trim(), charset[1].trim());
        }

        String value = line.substring(firstValueIndex, lastValueIndex).trim();
        headerMap.put(key, value);
    }

    private void parametersParser(String[] queryList) {
        Map<String, String> queryMap = getParameterMap();

        for (String query : queryList) {
            String[] parse = query.split("=");

            String key = parse[0].trim();
            String value = URLDecoder.decode(parse[1].trim(), StringUtils.DEFAULT_CHARSET);

            log.debug("[key : {} | value : {}]", key, value);
            queryMap.put(key, value);
        }
        headerMap.put(KEY_QUERY_PARAM_MAP, queryMap);
    }

    private void firstLineParser(String line) {
        log.debug("{}", line);
        boolean queryStringExist = false;

        String httpRequestMethod;
        String httpRequestPath;

        if (StringUtils.isNullOrEmpty(line)) { return; }

        if (line.contains("GET") || line.contains("POST")) {
            String[] data = line.split(" ");

            // =========================================================================
            // method
            httpRequestMethod = data[0];
            headerMap.put(KEY_HTTP_METHOD, httpRequestMethod);
            isBodyRequest = httpRequestMethod.equals("POST");
            // =========================================================================
            // path
            int urlLastIndex = data[1].length();
            if (data[1].contains("?")) {    // 만약, queryString 이 존재 한다면
                urlLastIndex = data[1].indexOf("?");
                queryStringExist = true;
            }
            httpRequestPath = data[1].substring(0, urlLastIndex);
            headerMap.put(KEY_REQUEST_PATH, httpRequestPath);
            // =========================================================================
            // query string
            if (queryStringExist) {
                String[] queryList = data[1].substring(urlLastIndex + 1)
                                            .split("&");
                parametersParser(queryList);
            }
            // =========================================================================
        }
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
        if (StringUtils.isNullOrEmpty(name)) {
            throw new IllegalArgumentException("key is Null!");
        }
        if (Objects.isNull(object)) {
            throw new IllegalArgumentException("object is Null!");
        }
        attributeMap.put(name, object);
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
        return getHeader(KEY_HOST);
    }

    @Override
    public String getMethod() {
        return getHeader(KEY_HTTP_METHOD);
    }

    @Override
    public String getRequestURI() {
        return getHeader(KEY_REQUEST_PATH);
    }

    /**
     * HTTP Method POST Request 를 하면, Body 의 length 를 반환
     * 
     * @return 만약, 값이 존재하지 않는다면, "0" 를 의도적으로 반환
     */
    private String getContentLength() {
        return Optional.ofNullable(getHeader(KEY_CONTENT_LENGTH))
                .orElse("0");
    }

    @Override
    public String getHeader(String name) {
        return Optional.ofNullable(String.valueOf(headerMap.get(name)))
                .orElseThrow(IllegalAccessError::new);
    }
}
