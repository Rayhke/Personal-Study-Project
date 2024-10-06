package com.nhnacademy.server.method.response;

import com.nhnacademy.server.method.response.exception.ResponseNotFoundException;
import com.nhnacademy.server.method.response.impl.EchoResponse;
import com.nhnacademy.server.method.response.impl.PortResponse;
import com.nhnacademy.server.method.response.impl.TimeResponse;
import com.nhnacademy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ResponseFactory {

    private static final List<Response> responseList = new ArrayList<>(
            List.of(
                    new EchoResponse(),
                    new TimeResponse(),
                    new PortResponse()
            )
            // Arrays.asList()
    );

    private ResponseFactory() {}

    /**
     * <h6>stream 구조 설명</h6>
     * 1. responseList 를 Stream 타입으로 변환 <br>
     * 2. 람다식으로 구현된 구조이며, 조건에 성립하는 원소들만 간추려냄 <br>
     * 3. 조건에 성립하는 목록 중, 가장 첫번 째 원소를 반환 <br>
     * 4. 만약 아무것도 찾지 못하면, 예외처리 실행 <hr>
     *
     * @param method 클래스를 구분하는 값
     * @return method 값에 부합하는 class 반환
     * @throws ResponseNotFoundException 존재하지 않는 method 일 경우
     */
    public static Response getResponse(String method) {
        if (StringUtils.isNullOrEmpty(method)) {
            throw new IllegalArgumentException("method is Null!");
        }

        return responseList.stream()
                .filter(response -> response.validate(method))
                .findFirst()
                .orElseThrow(() -> new ResponseNotFoundException(method));
    }
}
