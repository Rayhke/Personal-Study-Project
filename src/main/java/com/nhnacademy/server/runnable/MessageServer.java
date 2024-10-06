package com.nhnacademy.server.runnable;

import com.nhnacademy.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class MessageServer implements Runnable {

    private static final int DEFAULT_PORT = 8888;

    private final int port;

    private final ServerSocket serverSocket;

    private final WorkerThreadPool workerThreadPool;

    private final RequestChannel requestChannel;

    private static final Map<String, Socket> clientMap = new ConcurrentHashMap<>();

    public MessageServer() {
        this(DEFAULT_PORT);
    }

    public MessageServer(int port) {
        if (port < 0 || 65535 < port) {
            throw new IllegalArgumentException("port is range Out!");
        }

        this.port = port;

        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            if (e instanceof BindException) {
                log.error("이미 사용 중인, Port 번호 입니다 : {}", port);
            }
            throw new RuntimeException();
        }
    }

    // =================================================================================================================
    // thread

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                Socket client = serverSocket.accept();

            } catch (Exception e) {
                log.error("{}", e.getMessage(), e);
            }
        }
    }

    // =================================================================================================================
    // method

    public static boolean addClient(String id, Socket socket) {
        if (clientMap.containsKey(id)) {
            log.debug("이미 존재하는 Socket ID 입니다. : {}", id);
            return false;
        }
        clientMap.put(id, socket);
        return true;
    }

    public static List<String> getClientIdList() {
        return clientMap.keySet().stream().collect(Collectors.toList());
    }

    public static Socket getClientSocket(String id) {
        return clientMap.get(id);
    }

    public static void removeClient(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            log.debug("삭제하고자 하는 Socket ID를 올바르게 입력해주세요.");
            return;
        }

        if (Objects.isNull(clientMap.remove(id))
                || clientMap.containsKey(id)) {
            log.debug("Socket 삭제 실패 : {}", id);
        }
    }
}
