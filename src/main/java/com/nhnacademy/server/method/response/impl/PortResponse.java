package com.nhnacademy.server.method.response.impl;

import com.nhnacademy.server.method.parser.PortParser;
import com.nhnacademy.server.method.parser.PortParser.AddressAndPort;
import com.nhnacademy.server.method.response.Response;
import com.nhnacademy.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

@Slf4j
public class PortResponse implements Response {

    private static final String METHOD = "port";

    @Override
    public String getMethod() {
        return METHOD;
    }

    @Override
    public String execute(String value) {
        boolean isFetchAll = false;
        if (StringUtils.isNullOrEmpty(value)) { isFetchAll = true; }

        StringBuilder sb = new StringBuilder();
        try {
            // 우분투 환경 기준
            Process process = Runtime.getRuntime().exec("lsof -n -i -P");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("(LISTEN)")) {
                    AddressAndPort info = PortParser.parse(line);
                    if (Objects.isNull(info)) { continue; }

                    String result = String.format("%s %s %s%n",
                                            info.getMode(), info.getAddress(), info.getPort());
                    if (isFetchAll || info.getPort().contains(value)) {
                        sb.append(result);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
