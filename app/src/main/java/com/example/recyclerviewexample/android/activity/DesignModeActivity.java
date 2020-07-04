package com.example.recyclerviewexample.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.designmode.builder.User;

public class DesignModeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        buildDesign();
    }

    private void buildDesign() {
        new User.UserBuilder(100,"mao")
                .setAddress("m")
                .setAge(12)
                .setPhone("1");
    }
}
