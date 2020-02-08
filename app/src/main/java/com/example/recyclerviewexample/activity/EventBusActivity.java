package com.example.recyclerviewexample.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.eventbus.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EventBusActivity extends BaseActivity {

    private Button mButton;
    private EditText mEditTextSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);

        mButton = findViewById(R.id.btn_go);
        mEditTextSave = findViewById(R.id.et_save);
        mButton.setOnClickListener((v) -> startActivity(new Intent(this,GlideActivity.class)));
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(EventMessage eventMessage) {
        Toast.makeText(this, "eventbus ok  MAIN", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_event_bus;
    }

    @Override
    public void initView() {

    }

    @Override
    public void init() {

    }
}
