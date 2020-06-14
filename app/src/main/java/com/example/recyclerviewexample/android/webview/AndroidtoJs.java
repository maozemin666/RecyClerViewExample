package com.example.recyclerviewexample.android.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

public class AndroidtoJs {
    private static final String TAG = "maomaoAndroidtoJs";
    @JavascriptInterface
    public void hello(String msg) {
        Log.d(TAG, "hello: "+msg);
    }
}
