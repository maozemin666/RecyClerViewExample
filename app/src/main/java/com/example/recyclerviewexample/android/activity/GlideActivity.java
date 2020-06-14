package com.example.recyclerviewexample.android.activity;

import android.os.Bundle;
import android.widget.ImageView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.thirdparty.glide.GlideDemo;

public class GlideActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_glide;
    }

    @Override
    public void initView() {
        mImageView = findViewById(R.id.iv_glide);
    }

    @Override
    public void init() {
        GlideDemo glideDemo = new GlideDemo(mImageView, this);
//        glideDemo.loadNetworkStillPicture();
//        glideDemo.loadByteArray();
//        glideDemo.thumbnail();
//        glideDemo.customTarget();
        glideDemo.viewTarget();
    }
}
