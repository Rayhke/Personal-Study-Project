package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.server.runnable.MessageServer;
import com.nhnacademy.server.thread.channel.Session;
import com.nhnacademy.util.StringUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WhisperResponse implements Response {

    private static final String METHOD = "whisper";

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String execute(String value) {
        if (!Session.isLogin()) {
            return "login required!";
        }
        if (StringUtils.isNullOrEmpty(value)) {
            return "empty message!";
        }
        Whisper whisper = new Whisper(value);
        whisper.inputTry();
        return whisper.result();
    }

    // =================================================================================================================
    // class

    private final class Whisper {

        private final String id;

        private final String message;

        /**
         * <h5>데이터 양식은 아래와 같습니다.</h5>
         * - "marco hello"              : (보내는 대상 : O, 메세지 : O) (O) <br>
         * - "marco"                    : (보내는 대상 : O, 메세지 : X) (X) <br>
         * - ""                         : (보내는 대상 : X, 메세지 : X) (X) <br>
         * - "marco nico to meet you"   : (보내는 대상 : O, 메세지 : O) (O)
         *
         * @param value input data
         */
        public Whisper(String value) {
            String[] data = value.split(" ");
            String id = "";
            String message = "";

            // 입력된 데이터 양식이 올바른 경우에만 동작
            if (data.length > 1) {
                id = data[0];
                message = value.substring(id.length());
            }
            this.id = id;
            this.message = message;
        }

        // =============================================================================================================
        // Parameters Method

        public String getId() {
            return id;
        }

        public String getMessage() {
            return message;
        }

        // =============================================================================================================
        // Util Method

        public boolean isEmpty() {
            return StringUtils.isNullOrEmpty(getId())
                    || StringUtils.isNullOrEmpty(getMessage());
        }

        public void inputTry() {
            if (isEmpty()) { return; }

            Socket client = MessageServer.getClientSocket(getId());
            try (PrintWriter output = new PrintWriter(client.getOutputStream())
            ) {
                output.println(String.format("[%s] %s", Session.getCurrentId(), message));
                output.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public String result() {
            return String.format("[%s] [%s] : %s", METHOD, id, message);
        }
    }
}
