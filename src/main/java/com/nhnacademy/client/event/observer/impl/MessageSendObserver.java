package com.nhnacademy.client.event.observer.impl;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.subject.EventType;
import com.nhnacademy.util.StringUtils;

import java.util.Objects;

public class MessageSendObserver implements Observer {

    private final MessageAction sendMessageAction;

    public MessageSendObserver(MessageAction sendMessageAction) {
        if (Objects.isNull(sendMessageAction)) {
            throw new IllegalArgumentException("messageAction is Null!");
        }
        this.sendMessageAction = sendMessageAction;
    }

    @Override
    public EventType getEventType() {
        return EventType.SEND;
    }

    @Override
    public void updateMessage(String message) {
        if (StringUtils.isNullOrEmpty(message)) {
            throw new IllegalArgumentException("message is Null!");
        }
        sendMessageAction.execute(message);
    }
}
