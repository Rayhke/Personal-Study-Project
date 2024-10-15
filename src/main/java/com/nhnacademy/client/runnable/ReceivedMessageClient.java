package com.nhnacademy.client.runnable;

import com.nhnacademy.client.event.subject.Subject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class ReceivedMessageClient implements Runnable {

    private final Socket socket;

    private final Subject subject;

    public ReceivedMessageClient(Socket socket, Subject subject) {
        if (Objects.isNull(socket)) {
            throw new IllegalArgumentException("socket is Null!");
        }
        if (Objects.isNull(subject)) {
            throw new IllegalArgumentException("subject is Null!");
        }
        this.socket = socket;
        this.subject = subject;
    }

    @Override
    public void run() {
        try (InputStream inputStream = socket.getInputStream();
             BufferedReader input = new BufferedReader(new InputStreamReader(inputStream))
        ) {
            String line = null;
            while ((line = input.readLine()) != null) {
                log.debug("recv-message : {}", line);
                subject.receiveMessage(line);
            }
        } catch (IOException e) {
            log.error("receivedMessage Error : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            close();
        }
    }
    
    private void close() {
        if (Objects.nonNull(socket)) {
            try {
                if (!socket.isClosed()) socket.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
