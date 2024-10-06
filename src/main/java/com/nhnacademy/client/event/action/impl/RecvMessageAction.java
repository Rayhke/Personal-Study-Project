package com.nhnacademy.client.event.action.impl;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.ui.form.MessageClientForm;

import java.util.Objects;

public class RecvMessageAction implements MessageAction {

    private final MessageClientForm messageClientForm;

    // messageClientForm 을 초기화 합니다. messageClientForm 은 메시지 전송/수신 UI를 담당 합니다.
    public RecvMessageAction(MessageClientForm messageClientForm) {
        if (Objects.isNull(messageClientForm)) {
            throw new IllegalArgumentException("messageClientForm is Null!");
        }
        this.messageClientForm = messageClientForm;
    }

    @Override
    public void execute(String message) {
        /* message 를 수신하면 MessageRecvObserver 에 의해서 호출 됩니다.
            - messageClientForm.getMessageArea()에 message 를 추가 합니다.
            - message 추가 후 개행 문자를 추가로 삽입 합니다.
         */
        // %n 보단, System.lineSeparator() 를 적극적으로 써라
        // 운영체제에 따라, 개행 문자의 방식이 다르다.
        messageClientForm.getMessageArea().append(message);
        messageClientForm.getMessageArea().append(System.lineSeparator());
    }
}
