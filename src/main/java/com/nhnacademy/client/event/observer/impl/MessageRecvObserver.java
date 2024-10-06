package com.nhnacademy.client.event.observer.impl;

import com.nhnacademy.client.event.action.MessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.subject.EventType;
import com.nhnacademy.util.StringUtils;

import java.util.Objects;

public class MessageRecvObserver implements Observer {

    private final MessageAction recvMessageAction;

    public MessageRecvObserver(MessageAction recvMessageAction) {
        if (Objects.isNull(recvMessageAction)) {
            throw new IllegalArgumentException("messageAction is Null!");
        }
        this.recvMessageAction = recvMessageAction;
    }

    @Override
    public EventType getEventType() {
        return EventType.RECV;
    }

    @Override
    public void updateMessage(String message) {
        if (StringUtils.isNullOrEmpty(message)) {
            throw new IllegalArgumentException("message is Null!");
        }
        recvMessageAction.execute(message);
    }
}
