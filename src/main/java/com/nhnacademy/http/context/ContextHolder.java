package com.nhnacademy.http.context;

import com.nhnacademy.http.context.impl.ApplicationContext;

/**
 * Context 가 Web Server 내에서 공유 자원으로 사용됩니다.
 */
public class ContextHolder {

    private static final Context context = new ApplicationContext();

    private ContextHolder() {}

    public static synchronized Context getApplicationContext() {
        return context;
    }
}
