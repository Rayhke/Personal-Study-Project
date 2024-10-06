package com.nhnacademy.client.event.subject;

public enum EventType {

    SEND("송신"),
    RECV("수신");

    private String value;

    EventType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
