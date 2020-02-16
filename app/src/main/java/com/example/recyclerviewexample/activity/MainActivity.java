package com.example.recyclerviewexample.activity;

import android.content.Intent;
import android.widget.Button;

import com.example.recyclerviewexample.R;

public class MainActivity extends BaseActivity {

    private Button btn_java_base;
    private Button btn_custome_view;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btn_java_base = findViewById(R.id.btn_java_base);
        btn_custome_view = findViewById(R.id.btn_custome_view);
        btn_java_base.setOnClickListener(v -> startActivity(new Intent(this, JavaBaseActivity.class)));
        btn_custome_view.setOnClickListener(v -> startActivity(new Intent(this, CustomeViewActivity.class)));
    }

    @Override
    public void init() {

    }

}
