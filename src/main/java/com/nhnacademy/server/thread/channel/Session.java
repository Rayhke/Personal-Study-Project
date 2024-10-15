package com.nhnacademy.server.thread.channel;

import com.nhnacademy.util.StringUtils;

import java.net.Socket;
import java.util.Objects;

public final class Session {

    private static final ThreadLocal<Socket> currentClientLocal = new ThreadLocal<>();

    private static final ThreadLocal<String> currentIdLocal = new ThreadLocal<>();

    public static synchronized void initializedClient(Socket client) {
        if (Objects.isNull(client)) {
            throw new IllegalArgumentException("client is Null!");
        }
        currentClientLocal.set(client);
    }

    public static synchronized void initializedId(String id) {
        if (StringUtils.isNullOrEmpty(id)) {
            throw new IllegalArgumentException("id is Null!");
        }
        currentIdLocal.set(id);
    }

    public static synchronized void reset() {
        currentClientLocal.remove();
        currentIdLocal.remove();
    }

    public static Socket getCurrentClient() {
        return currentClientLocal.get();
    }

    public static String getCurrentId() {
        return currentIdLocal.get();
    }

    public static boolean isLogin() {
        return Objects.nonNull(currentIdLocal.get());
    }
}
