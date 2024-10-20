package com.nhnacademy.http.service;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.service.exception.MethodNotAllowed;

public interface HttpService {

    default void service(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getMethod().equals("GET")) {
            doGet(httpRequest, httpResponse);
        } else if (httpRequest.getMethod().equals("POST")) {
            doPost(httpRequest, httpResponse);
        }
    }

    default void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowed(httpRequest.getRequestURI(), httpRequest.getMethod());
    }

    default void doPost(HttpRequest httpRequest, HttpResponse httpResponse) {
        throw new MethodNotAllowed(httpRequest.getRequestURI(), httpRequest.getMethod());
    }
}
