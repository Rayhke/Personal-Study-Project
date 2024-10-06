package com.nhnacademy.server.main;

import com.nhnacademy.server.runnable.MessageServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerMain {

    public static void main(String[] args) {
        MessageServer messageServer = new MessageServer();
        Thread thread = new Thread(messageServer);
        thread.start();
    }
}
