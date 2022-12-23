package com.example.tz_demo.common;

import com.example.tz_demo.entity.ManageUser;

public class BaseContext {
    private final static ThreadLocal<ManageUser> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static void setUser(ManageUser user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static ManageUser getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void removeUser() {
        USER_THREAD_LOCAL.remove();
    }
}
