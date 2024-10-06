package com.nhnacademy.client.main;

import com.nhnacademy.client.runnable.MessageClient;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClientMain {

    public static void main(String[] args) {
        MessageClient messageClient = new MessageClient();
        Thread thread = new Thread(messageClient);
        thread.start();
    }
}
