package com.example.recyclerviewexample.activity;

import android.content.Intent;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.recyclerviewexample.activity.base.BaseView;

public abstract class BaseActivity extends AppCompatActivity implements BaseView {
    private static final String TAG = "BaseActivity";
    private String CLASS_NAME = "CLASS_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        CLASS_NAME = getClass().getSimpleName();
        Log.d(TAG, "onCreate: "+CLASS_NAME);
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        init();
    }

    @Override
    public void onContentChanged() {
        Log.d(TAG, "onContentChanged: "+CLASS_NAME);
        super.onContentChanged();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: "+CLASS_NAME);
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: "+CLASS_NAME);
        super.onStart();
        /*
         * 因此如果我们在onStop()当中反注册了一些广播，或者释放了一些资源，那么在这里需要重新注册或者初始化
         */
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: "+CLASS_NAME);
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: "+CLASS_NAME);
        super.onNewIntent(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.d(TAG, "onActivityResult: "+CLASS_NAME);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: "+CLASS_NAME);
        super.onResume();
        /*
        在可见时重新拉取一些需要及时刷新的数据、注册 ContentProvider的监听，最重要的是在onPause()中的一些释放操作要在这里面恢复回来
         */
    }

    @Override
    public void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow: "+CLASS_NAME);
        super.onAttachedToWindow();
    }

    @Override
    public void onDetachedFromWindow() {
        Log.d(TAG, "onDetachedFromWindow: "+CLASS_NAME);
        super.onDetachedFromWindow();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: "+CLASS_NAME);
        super.onPause();

        /*
        不应该在这里执行耗时的操作，因为新界面启动的时候会先回调当前页面的onPause()方法，

        */
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: "+CLASS_NAME);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: "+CLASS_NAME);
        super.onStop();
        /*
        例如先前注册的广播、使用的传感器（如GPS）、以及一些仅当页面获得焦点时才需要的资源
         */
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: "+CLASS_NAME);
        super.onDestroy();
    }
}
