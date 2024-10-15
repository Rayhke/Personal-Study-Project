package com.nhnacademy.http.context;

/**
 * Application 이 구동되는 환경을 Context 라고 합니다.
 */
public interface Context {

    /**
     * Key 값에 대응하는 Value Object 를 반환합니다.
     *
     * @param name Key
     * @return Value
     */
    Object getAttribute(String name);

    /**
     * Key 값과 Value Object 를 매칭 시킵니다.
     *
     * @param name Key
     * @param object Value
     */
    void setAttribute(String name, Object object);

    /**
     * Key 값에 대응하는 Value Object 를 제거합니다.
     *
     * @param name Key
     */
    void removeAttribute(String name);
}
