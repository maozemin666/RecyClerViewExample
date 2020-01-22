package com.example.recyclerviewexample.Thread;

import android.util.Log;

public class RunnableAchieve implements Runnable {
    private static final String TAG = "maomaoRunnableAchieve";
    @Override
    public void run() {
        Log.d(TAG, "run: ");
    }
}
