package com.example.recyclerviewexample.android.view;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.recyclerviewexample.R;

public class RefreshAndMoreActivity1 extends AppCompatActivity {
    private YCRefreshView recyclerview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_and_more1);
        initView();

    }

    private void initView() {
        recyclerview = findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLinearLayoutManager(linearLayoutManager);

    }
}
