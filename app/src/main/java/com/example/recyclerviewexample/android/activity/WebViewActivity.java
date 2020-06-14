package com.example.recyclerviewexample.android.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.recyclerviewexample.R;

import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.KeyEvent.KEYCODE_FORWARD;

public class WebViewActivity extends BaseActivity {
    private static final String TAG = "maomaoWebViewActivity";
    private WebView webView;
    private WebSettings webSettings;
    private Button javaToJs;
    private Button jsBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewSet();
        setWebViewCache();
        javaToJs();
        jsToJava();
        jsBridge();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initView() {
        webView = findViewById(R.id.web_view);
        javaToJs = findViewById(R.id.btn_java_to_js);
        jsBridge = findViewById(R.id.btn_js_bridge);
        jsBridge.setOnClickListener(v -> startActivity(new Intent(this, JsBridgeWebViewActivity.class)));
    }

    @Override
    public void init() {

    }

    private void webViewSet() {
        webView.loadUrl("file:///android_asset/javascript.html");
        webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setPluginState(WebSettings.PluginState.OFF); //支持flash插件

//        设置自适应屏幕，两者适用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); //缩放至屏幕大小

//        缩放操作
        webSettings.setSupportZoom(true); //支持缩放,默认为true,是下面的前提
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件.若为false,则该webview不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //关闭webview当中的缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过js打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8"); //设置编码格式
    }

    private void setWebViewCache() {
        boolean isNetWork = true;
        if (isNetWork) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 没网则从本地获取
        }

        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setAppCachePath(getFilesDir().getAbsolutePath()+"cachePath");
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); //设置https和http混合使用

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();    //表示等待证书响应
                // handler.cancel();      //表示挂起连接，为默认方式
                // handler.handleMessage(null);    //可做其他处理

            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }

    private void javaToJs() {
//        1.
//        javaToJs.setOnClickListener(v -> webView.loadUrl("javascript:callJS()"));
//        2.
        javaToJs.setOnClickListener(v -> {
            webView.evaluateJavascript("javascript:callJS()", (value) -> {
                Log.d(TAG, "javaToJs: value="+value);
            });
        });
    }

    private void jsToJava() {
//        1.通过WebView的addJavascriptInterface（）进行对象映射
//        webView.addJavascriptInterface(new AndroidtoJs(), "test");
    }

    private void jsBridge() {
        
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        } else if ((keyCode == KEYCODE_FORWARD) && webView.canGoForward()) {
            webView.goForward();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
