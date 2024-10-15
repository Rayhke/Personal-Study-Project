package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runnable.MessageServer;
import com.nhnacademy.server.thread.channel.Session;
import com.nhnacademy.util.StringUtils;

import java.util.List;

public class LoginResponse implements Response {

    private static final String METHOD = "login";

    @Override
    public String getMethod() {
        return METHOD;
    }

    /**
     * 기본적으로는 접속을 시도하는 client 를 등록 하지만,<br>
     * 'list' 라는 입력을 받으면, 현재 접속 중인 client 들의 정보를 반환합니다.
     *
     * @param value clientId or listPrint
     * @return 작업을 수행한 결과
     */
    @Override
    public String execute(String value) {
        if (StringUtils.isNullOrEmpty(value)) {
            throw new IllegalArgumentException("value is Null!");
        }
        return (value.equals("list")) ?
                clientList()
                : clientLoginTry(value);
    }

    // =================================================================================================================
    // private method

    private String clientList() {
        List<String> clientIdList = MessageServer.getClientIdList();
        return (clientIdList.isEmpty()) ?
                "empty"
                : String.join(System.lineSeparator(), clientIdList);

    }

    private String clientLoginTry(String value) {
        boolean loginSuccess = MessageServer.addClient(value, Session.getCurrentClient());
        if (loginSuccess) { Session.initializedId(value); }
        return String.format("%s %s!", METHOD, (loginSuccess) ? "success" : "fail");
    }
}
