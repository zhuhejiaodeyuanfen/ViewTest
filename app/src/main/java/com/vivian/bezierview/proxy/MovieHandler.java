package com.vivian.bezierview.proxy;

import java.lang.reflect.Method;

public class MovieHandler implements java.lang.reflect.InvocationHandler {

    private RealMovie realMovie;

    public MovieHandler(RealMovie realMovie) {
        this.realMovie = realMovie;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("电影播放前");
        method.invoke(realMovie,args);
        System.out.println("电影播放后");
        return null;
    }
}
