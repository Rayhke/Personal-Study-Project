package com.nhnacademy.http.request;

import java.util.Map;

public interface HttpRequest {

    Object getAttribute(String name);

    void setAttribute(String name, Object object);

    Map<String, String> getParameterMap();

    String getParameter(String name);

    String getHost();

    String getMethod();

    String getRequestURI();

    String getHeader(String name);
}
