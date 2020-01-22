package com.example.recyclerviewexample.okhttp;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class DoraemonWeakNetworkInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (isNetWorkActive()) {
           Request request = chain.request();
           return chain.proceed(request);
        }




        return null;
    }

//    网络活跃
    public static boolean isNetWorkActive() {
        return false;
    }
}
