package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.thread.channel.Session;

public class WhoamiResponse implements Response {

    private static final String METHOD = "whoami";

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String execute(String value) {
        return (Session.isLogin()) ?
                String.format("my id is [%s]", Session.getCurrentId())
                : "login required!";
    }
}
