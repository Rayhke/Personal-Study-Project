package com.nhnacademy.client.event.subject;

import com.nhnacademy.client.event.observer.Observer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageSubject implements Subject {

    private final List<Observer> observers;

    public MessageSubject() {
        // List 자체를 synchronized 화 시키는 것
        observers = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void register(EventType eventType, Observer observer) {
        observers.add(observer);
    }

    @Override
    public void remove(EventType eventType, Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(EventType eventType, String message) {
        // observers 내에 특정 eventType 을 지닌,
        // observer 구현체의 메세지를 업데이트한다.
        observers.stream()
                    .filter(observer -> observer.validate(eventType))
                    .forEach(observer -> observer.updateMessage(message));
    }
}
