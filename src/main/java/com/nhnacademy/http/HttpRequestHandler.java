package com.nhnacademy.http;

import com.nhnacademy.http.channel.Executable;
import com.nhnacademy.http.channel.RequestChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class HttpRequestHandler implements Runnable {

    private final RequestChannel requestChannel;

    public HttpRequestHandler(RequestChannel requestChannel) {
        if (Objects.isNull(requestChannel)) {
            throw new IllegalArgumentException("requestChannel is Null!");
        }
        this.requestChannel = requestChannel;
    }

    // =================================================================================================================
    // thread

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Executable httpJob = requestChannel.getHttpJob();
                httpJob.execute();
            } catch (Exception e) {
                if (e.getMessage()
                        .contains(InterruptedException.class.getName())) {
                    Thread.currentThread().interrupt();
                }
                log.error("RequestHandler error : {}", e.getMessage(), e);
            }
        }
    }
}
