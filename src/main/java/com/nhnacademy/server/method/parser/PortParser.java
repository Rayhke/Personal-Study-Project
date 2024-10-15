package com.nhnacademy.server.method.parser;

import com.nhnacademy.util.StringUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public final class PortParser {

    public enum Mode {
        TCP, UDP
    }

    private static final Pattern PATTERN = Pattern.compile("((\\d{1,3}(?:\\.\\d{1,3}){3})|\\*):(\\S+)(?:->((\\d{1,3}(?:\\.\\d{1,3}){3})|\\*):(\\S+))?");
    // "\\d"    : 0 ~ 9 사이의 숫자 ("[0-9]" 의 의미와 이하 동일)
    // "{n}"    : n번만 반복
    // "{n,m}"  : n ~ m번 반복
    // "?:"     : 찾기는 한데 그룹에 포함되지 않는다?
    // "+"      : 1개 이상 있을 수도 있다.
    // "-"      : ~ 와 같은 의미 (그 사이 or 범위)
    // "|"      : or 연산자
    // "\\s"    : 공백, 탭만 인식
    // "\\S"    : 공백, 탭이 아닌 문자만 인식

    private PortParser() {}

    public static AddressAndPort parse(String message) {
        if (StringUtils.isNullOrEmpty(message)) {
            return null;
        }

        // 반드시 앞에 TCP 인지 UDT 인지 구분되어야 합니다.
        Mode mode;
        int index = message.lastIndexOf(Mode.TCP.toString());
        if (index != -1) {
            mode = Mode.TCP;
        } else {
            index = message.lastIndexOf(Mode.UDP.toString());
            if (index == -1) {
                return null;
            }
            mode = Mode.UDP;
        }

        String[] data = message.substring(index).split(" ");
        return Arrays.stream(data)
                .filter(str -> PATTERN.matcher(str).matches())
                .map(str -> {
                    String[] input;
                    String localIP = null;
                    String localPort = null;

                    if (!str.contains("->")) {
                        input = str.split(":");
                        localIP = input[0];
                        localPort = input[1];
                    } else {
                        input = str.split("->");
                        String[] local = input[0].split(":");
                        // String[] remote = input[1].split(":"); // 훗날 사용할 수도..
                        localIP = local[0];
                        localPort = local[1];
                    }

                    if (StringUtils.isNullOrEmpty(localIP)
                            || StringUtils.isNullOrEmpty(localPort)) {
                        return null;
                    }
                    return new AddressAndPort(mode, localIP, localPort);
                })
                .findFirst()
                .orElse(null);
    }

    public static final class AddressAndPort {

        private final Mode mode;

        private final String address;

        private final String port;

        public AddressAndPort(Mode mode, String address, String port) {
            if (Objects.isNull(mode)) {
                throw new IllegalArgumentException("mode is Null!");
            }

            if (StringUtils.isNullOrEmpty(address)) {
                throw new IllegalArgumentException("address is Null!");
            }

            // port <= 0 || 65536 <= port
            if (StringUtils.isNullOrEmpty(port)) {
                throw new IllegalArgumentException("port is range Out!");
            }

            this.mode = mode;
            this.address = address;
            this.port = port;
        }

        public Mode getMode() {
            return mode;
        }

        public String getAddress() {
            return address;
        }

        public String getPort() {
            return port;
        }
    }
}
