package com.example.recyclerviewexample.activity;

import android.content.Intent;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;

public class GlideActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide);

        mImageView = findViewById(R.id.iv_glide);
        mImageView.setBackgroundColor(Color.RED);
        mImageView.setOnClickListener((v) -> {
            EventBus.getDefault().post(new EventMessage(1,"mao"));
            startActivity(new Intent(this,EventBusActivity.class));
        });
        initGlide();
    }

    private void initGlide() {

    }
}
