package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.widget.Button;

import com.example.recyclerviewexample.R;
import com.github.lzyzsd.jsbridge.BridgeWebView;

public class JsBridgeWebViewActivity extends BaseActivity {
    private static final String TAG = "maomaoJsBridgeWebViewActivity";

    private BridgeWebView webView;
    private Button callJsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_js_bridge_web_view;
    }

    @Override
    public void initView() {
        webView = findViewById(R.id.jsbridge_web_view);
        callJsButton = findViewById(R.id.btn_call_js);
    }

    @Override
    public void init() {
        webView.loadUrl("file:///android_asset/demo.html");
        callJsButton.setOnClickListener(v -> {
            webView.callHandler("functionInJs", "data from Java", (data) -> {
                Log.i(TAG, "reponse data from js " + data);
            });
        });

        webView.setWebChromeClient(new WebChromeClient() {

        });
    }
}
