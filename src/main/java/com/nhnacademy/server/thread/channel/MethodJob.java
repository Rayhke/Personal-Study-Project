package com.nhnacademy.server.thread.channel;

import com.nhnacademy.server.method.parser.MethodParser;
import com.nhnacademy.server.method.parser.MethodParser.MethodAndValue;
import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.method.response.ResponseFactory;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Objects;

@Slf4j
public class MethodJob implements Executable {

    private final Socket client;

    public MethodJob(Socket client) {
        if (Objects.isNull(client) || client.isClosed()) {
            throw new IllegalArgumentException("client is Null!");
        }
        this.client = client;
    }

    @Override
    public void execute() {
        try (
                BufferedReader clientIn = new BufferedReader(new InputStreamReader(client.getInputStream()));
                PrintWriter out = new PrintWriter(client.getOutputStream(), false);
        ) {
            InetAddress inetAddress = client.getInetAddress();
            log.debug("ip : {}, port : {}", inetAddress.getAddress(), client.getPort());

            String recvMessage;

            while ((recvMessage = clientIn.readLine()) != null) {
                System.out.println("recv-message : " + recvMessage);

                MethodAndValue methodAndValue = MethodParser.parse(recvMessage);
                log.debug("method : {},value : {}", methodAndValue.getMethod(), methodAndValue.getValue());

                Response response = ResponseFactory.getResponse(methodAndValue.getMethod());

                String sendMessage;
                if (Objects.nonNull(response)) {
                    sendMessage = response.execute(methodAndValue.getValue());
                } else {
                    sendMessage = String.format("{%s} method not found!", methodAndValue.getMethod());
                }

                out.println(sendMessage);
                out.flush();
            }
        } catch (Exception e) {
            log.error("thread-error : {}", e.getMessage(), e);

            if (e instanceof InterruptedException) {
                Thread.currentThread().isInterrupted();
            }
        } finally {
            if (Objects.nonNull(client)) {
                try {
                    if (!client.isClosed()) client.close();
                    log.debug("client 정상 종료");
                } catch (IOException e) {
                    log.error("error-client-close : {}", e.getMessage(), e);
                }
            }
        }
    }
}
