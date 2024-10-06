package com.nhnacademy.client.event.action.impl;

import com.nhnacademy.client.event.action.MessageAction;

import java.io.PrintWriter;
import java.util.Objects;

public class SendMessageAction implements MessageAction {

    private final PrintWriter printWriter;

    // server 쪽으로 보내는 printWriter 를 연결
    public SendMessageAction(PrintWriter printWriter) {
        if (Objects.isNull(printWriter)) {
            throw new IllegalArgumentException("printWrite is Null!");
        }
        this.printWriter = printWriter;
    }

    @Override
    public void execute(String message) {
        if (Objects.isNull(message)) {
            throw new IllegalArgumentException("메세지를 입력해 주세요.");
        }
        printWriter.println(message);
        printWriter.flush();
    }
}
