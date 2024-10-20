package com.nhnacademy.http.service.impl;

import com.nhnacademy.http.request.HttpRequest;
import com.nhnacademy.http.response.HttpResponse;
import com.nhnacademy.http.service.HttpService;
import com.nhnacademy.http.util.CounterUtils;
import com.nhnacademy.http.util.ResponseUtils;
import com.nhnacademy.http.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.PrintWriter;

@Slf4j
public class IndexHttpService implements HttpService {

    public static final String URI = "/index.html";

    @Override
    public void doGet(HttpRequest httpRequest, HttpResponse httpResponse) {
        String responseBody = ResponseUtils.tryGetBodyFromFile(httpRequest.getRequestURI());

        long count = CounterUtils.increaseAndGet();
        String userId = httpRequest.getParameter("userId");
        boolean userIdExist = !StringUtils.isNullOrEmpty(userId);

        log.debug("count : {}", count);
        log.debug("userId : {}", (userIdExist) ? userId : "Null");

        responseBody = responseBody.replace("${count}", String.valueOf(count));
        responseBody = (userIdExist) ?
                        responseBody.replace("${userId}", userId)
                                    .replace("회원가입이 완료 되었습니다!</h2>",
                                        "회원가입이 완료 되었습니다!</h2><ul><li><h3><a href=\"index.html\">Reset</a></h3></li></ul>")
                        : responseBody.replace("<h2>${userId}님의 회원가입이 완료 되었습니다!</h2>", "");

        String responseHeader = null;

        try (PrintWriter bufferedWriter = httpResponse.getWriter()
        ) {
            responseHeader = ResponseUtils.createResponseHeader(ResponseUtils.HttpStatus.OK.getCode(),
                                                                StringUtils.DEFAULT_CHARSET,
                                                                responseBody.getBytes(StringUtils.DEFAULT_CHARSET).length);
            bufferedWriter.write(responseHeader);
            bufferedWriter.write(responseBody);
            bufferedWriter.flush();
        } catch (Exception e) {
            log.error("{}", e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }
}
