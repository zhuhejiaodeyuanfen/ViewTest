package com.vivian.bezierview.proxy;

public class Movie implements MoviePlay {

//    private RealMovie realMovie;
//
//    public Movie(RealMovie realMovie) {
//        this.realMovie = realMovie;
//    }

    @Override
    public void play() {
        System.out.println("电影即将开始");
//        realMovie.play();
        System.out.println("电影即将结束");
    }
}
