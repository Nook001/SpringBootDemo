package com.sky.context;

public class BaseContext {

    public static ThreadLocal<Long> userId = new ThreadLocal<>();

    public static void setUserId(Long id) {
        userId.set(id);
    }

    public static Long getUserId() {
        return userId.get();
    }

    public static void removeCurrentId() {
        userId.remove();
    }

}
