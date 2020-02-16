package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.recyclerviewexample.R;

public class CustomeViewActivity extends BaseActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_custome_view;
    }

    @Override
    public void initView() {
        Button btn_labelflowlayout = findViewById(R.id.btn_labelflowlayout);
        btn_labelflowlayout.setOnClickListener(v -> new Intent(this, LabelFloLayoutActivity.class));
    }

    @Override
    public void init() {

    }
}
