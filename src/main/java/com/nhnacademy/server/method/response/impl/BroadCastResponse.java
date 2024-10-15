package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runnable.MessageServer;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

@Slf4j
public class BroadCastResponse implements Response {

    private static final String METHOD = "broadcast";

    @Override
    public String getMethod() {
        return METHOD;
    }

    /**
     * MessageServer.getClientIdList() 에 해당되는 모든 client 에게 message 를 전송합니다.
     *
     * @param value 공용 메세지
     * @return 작업을 수행한 결과
     */
    @Override
    public String execute(String value) {
        List<String> clientIdList = MessageServer.getClientIdList();
        int sendCount = 0;

        for (String clientId : clientIdList) {
            Socket client = MessageServer.getClientSocket(clientId);
            if (client.isClosed()) {
                if (Session.isLogin()) {
                    MessageServer.removeClient(Session.getCurrentId());
                }
                continue;
            }

            try (PrintWriter output = new PrintWriter(client.getOutputStream())
            ) {
                output.println(value);
                output.flush();
                sendCount++;
            } catch (Exception e) {
                log.error("{}", e.getMessage(), e);
            }
        }

        return String.format("{%d}명에게 \"{%S}\"를 전송 했습니다.", sendCount, value);
    }
}
