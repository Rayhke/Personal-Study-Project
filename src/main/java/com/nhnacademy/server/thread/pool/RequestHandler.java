package com.nhnacademy.server.thread.pool;

import com.nhnacademy.server.thread.channel.Executable;
import com.nhnacademy.server.thread.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class RequestHandler implements Runnable {

    private final RequestChannel requestChannel;

    public RequestHandler(RequestChannel requestChannel) {
        if (Objects.isNull(requestChannel)) {
            throw new IllegalArgumentException("requestChannel is Null!");
        }
        this.requestChannel = requestChannel;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Executable executable = requestChannel.getJob();
                executable.execute();
            } catch (Exception e) {
                if (e.getMessage().contains(InterruptedException.class.getName())) {
                    log.debug("thread 종료!");
                    Thread.currentThread().interrupt();
                }
                log.error("thread-exception : {}", e.getMessage(), e);
            }
        }
    }
}
