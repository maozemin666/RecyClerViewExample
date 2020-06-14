package com.example.recyclerviewexample.java.Thread;

import android.util.Log;

public class ThreadAchieve extends Thread{
    private static final String TAG = "maomaoThreadAchieve";
    @Override
    public void run() {
        Log.d(TAG, "run: ");
    }
}
