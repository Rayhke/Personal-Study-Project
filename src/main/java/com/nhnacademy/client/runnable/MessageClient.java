package com.nhnacademy.client.runnable;

import com.nhnacademy.client.event.action.impl.SendMessageAction;
import com.nhnacademy.client.event.observer.Observer;
import com.nhnacademy.client.event.observer.impl.MessageSendObserver;
import com.nhnacademy.client.event.subject.EventType;
import com.nhnacademy.client.event.subject.MessageSubject;
import com.nhnacademy.client.event.subject.Subject;
import com.nhnacademy.client.ui.form.MessageClientForm;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class MessageClient implements Runnable {

    // default server address
    private static final String DEFAULT_SERVER_ADDRESS = "localhost";

    // default server port
    private static final int DEFAULT_PORT = 8888;

    private final String serverAddress;

    private final int serverPort;

    private final Socket clientSocket;

    private final Subject subject;

    public MessageClient() {
        this(DEFAULT_SERVER_ADDRESS, DEFAULT_PORT);
    }

    public MessageClient(String serverAddress, int serverPort) {
        if (Objects.isNull(serverAddress) || serverAddress.isBlank()) {
            throw new IllegalArgumentException("serverAddress is Null!");
        }

        if (serverPort < 0 || 65535 < serverPort) {
            throw new IllegalArgumentException("serverPort is range Out!");
        }

        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.subject = new MessageSubject();

        try {
            clientSocket = new Socket(this.serverAddress, this.serverPort);

            if (clientSocket.isConnected()) {
                log.debug("client connect!");
                startReceivedMessageClient();
            }
        } catch (Exception e) {
            log.debug("create client socket error : {}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    // =================================================================================================================

    @Override
    public void run() {
        try (PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), false)
        ) {
            // 송신 Observer 설정
            configSendObserver(output);

            // Client 의 message 송수신 UI 호출
            MessageClientForm.showUI(subject);

            // 계속 Client 연결 유지
            while (!Thread.currentThread().isInterrupted()) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            log.debug("message : {}", e.getMessage(), e);
            log.debug("client close");
            /*if (e instanceof InterruptedException) {
                log.debug("exit!");
                Thread.currentThread().interrupt();
            }*/
        } finally {
            if (Objects.nonNull(clientSocket)) {
                try {
                    if (!clientSocket.isClosed()) clientSocket.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // =================================================================================================================

    private void startReceivedMessageClient() {
        /*receivedMessageClient를 이용해서 thread를 생성하고 시작 합니다.
            - message를 수신하는 역할
            - 즉 message의 수신과 송신을 비동기 적으로 처리하기 위함.
            - 지금 까지는 client -> message를 server에 전송 server는 client에게 응답하는 방식. 즉 동기방식
         */
        ReceivedMessageClient receivedMessageClient = new ReceivedMessageClient(clientSocket, subject);
        Thread thread = new Thread(receivedMessageClient);
        thread.start();
    }

    private void configSendObserver(PrintWriter printWriter) {
        SendMessageAction sendMessageAction = null;

        try {
            //sendMessageAction은 송신 event 발생시 MessageSendObserver Observer에 의해서 실제 송신을 당당하는 객체 입니다.

            //observer를 관리하는 subject에 observer를 등록 합니다. eventType : EventType.SEND
            sendMessageAction = new SendMessageAction(printWriter);
            Observer observer = new MessageSendObserver(sendMessageAction);

            // 위의 method 의 목적
            subject.register(EventType.SEND, observer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
