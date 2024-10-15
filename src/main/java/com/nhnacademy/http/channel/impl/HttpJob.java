package com.nhnacademy.http.channel.impl;

import com.nhnacademy.http.channel.Executable;
import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.request.impl.HttpRequestImpl;
import com.nhnacademy.http.response.HttpResponse;

import java.net.Socket;
import java.util.Objects;

public class HttpJob implements Executable {

    private final HttpRequest httpRequest;

    private final HttpResponse httpResponse;

    private final Socket client;

    public HttpJob(Socket client) {
        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("client Socket is Null!");
        }
        this.client = client;
        this.httpRequest = new HttpRequestImpl(this.client);
        this.httpResponse = new HttpResponseImpl(this.client);
    }
}
