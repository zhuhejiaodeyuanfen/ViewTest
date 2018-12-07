package com.vivian.bezierview.proxy;

import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {
        RealMovie realMovie = new RealMovie();
//        Movie movie = new Movie();
//        movie.play();

        MoviePlay moviePlay= (MoviePlay) Proxy.newProxyInstance(RealMovie.class.getClassLoader(),RealMovie.class.getInterfaces(),new MovieHandler(realMovie));

        moviePlay.play();
    }
}
