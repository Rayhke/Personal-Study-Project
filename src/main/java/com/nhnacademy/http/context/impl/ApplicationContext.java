package com.nhnacademy.http.context.impl;

import com.nhnacademy.http.context.Context;
import com.nhnacademy.http.context.exception.ContextParametersDeleteFail;
import com.nhnacademy.http.context.exception.ObjectNotFoundException;
import com.nhnacademy.http.util.StringUtils;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 객체를 생성 후, Context 에 (등록 / 삭제) 를 할 수 있습니다.
 * 즉, 공유할 수 있는 환경입니다.
 */
public class ApplicationContext implements Context {

    private final ConcurrentMap<String, Object> objectMap;

    public ApplicationContext() {
        this.objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public Object getAttribute(String name) {
        keyCheck(name);
        return Optional.ofNullable(objectMap.get(name))
                .orElseThrow(() -> new ObjectNotFoundException(name));
    }

    @Override
    public void setAttribute(String name, Object object) {
        keyCheck(name); valueCheck(object);
        objectMap.put(name, object);
    }

    @Override
    public void removeAttribute(String name) {
        keyCheck(name);
        if (Objects.isNull(objectMap.remove(name))) {
            throw new ContextParametersDeleteFail(name);
        }
    }

    // =================================================================================================================
    // private method

    private void keyCheck(String key) {
        if (StringUtils.isNullOrEmpty(key)) {
            throw new IllegalArgumentException("name is Null!");
        }
    }

    private void valueCheck(Object value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("object is Null!");
        }
    }
}
