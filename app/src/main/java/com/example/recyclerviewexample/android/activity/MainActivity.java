package com.example.recyclerviewexample.android.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.android.dialog.ListDialogFragment;

import java.util.Arrays;

public class MainActivity extends BaseActivity {

    private Button btn_java_base;
    private Button btn_custome_view;
    private Button custom_dialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        btn_java_base = findViewById(R.id.btn_java_base);
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
    }

    @Override
    public void init() {

    }

}
