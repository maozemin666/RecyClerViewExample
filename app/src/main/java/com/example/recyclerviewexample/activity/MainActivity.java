package com.example.recyclerviewexample.activity;

import android.content.Intent;
import android.widget.Button;

import com.example.recyclerviewexample.R;

public class MainActivity extends BaseActivity {

    private Button btn_java_base;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btn_java_base = findViewById(R.id.btn_java_base);
        btn_java_base.setOnClickListener(v -> startActivity(new Intent(this, JavaBaseActivity.class)));
    }

    @Override
    public void init() {

    }

}
