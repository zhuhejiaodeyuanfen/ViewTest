package com.vivian.bezierview.proxy;

public class RealMovie implements MoviePlay {


    @Override
    public void play() {
        System.out.println("播放电影中");
    }
}
