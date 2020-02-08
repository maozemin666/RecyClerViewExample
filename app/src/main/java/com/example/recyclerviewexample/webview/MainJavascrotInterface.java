package com.example.recyclerviewexample.webview;

import android.webkit.JavascriptInterface;

import com.github.lzyzsd.jsbridge.BridgeWebView;

public class MainJavascrotInterface {


    @JavascriptInterface
    public void submitFromWeb(String data, String callbackId) {

    }
}
