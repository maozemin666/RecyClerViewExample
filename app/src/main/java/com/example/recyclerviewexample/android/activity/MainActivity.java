package com.example.recyclerviewexample.android.activity;

import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.android.dialog.ListDialogFragment;

import java.util.Arrays;

public class MainActivity extends BaseActivity {
    private static final String TAG = "maomao";

    private Button btn_java_base;
    private Button btn_custome_view;
    private Button custom_dialog;
    private ViewGroup content_view;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btn_java_base = findViewById(R.id.btn_java_base);
        content_view = findViewById(R.id.content_view);
        btn_custome_view = findViewById(R.id.btn_custome_view);
        custom_dialog = findViewById(R.id.custom_dialog);
        btn_java_base.setOnClickListener(v -> startActivity(new Intent(this, JavaBaseActivity.class)));
        btn_custome_view.setOnClickListener(v -> startActivity(new Intent(this, CustomeViewActivity.class)));
        custom_dialog.setOnClickListener(v -> {
            ListDialogFragment listDialogFragment = new ListDialogFragment();
            listDialogFragment.setData(Arrays.asList("maomao1", "maomao2", "maomao3"));
            listDialogFragment.setTitle("select one to select");
            listDialogFragment.setOnSelectListener((position, text) -> Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show());
            listDialogFragment.show(getSupportFragmentManager(), "custom_dialog");
        });
        content_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: "+event.getAction());
                content_view.setVisibility(View.GONE);
                return false;
            }
        });
        content_view.setOnClickListener(v -> {
            Log.d(TAG, "setOnClickListener: ");
            content_view.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void init() {

    }

}
