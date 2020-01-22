package com.example.recyclerviewexample.Thread;

import java.util.concurrent.Callable;

public class CallableAchieve implements Callable<String> {
    @Override
    public String call() throws Exception {

        return "callable to thread";
    }
}
