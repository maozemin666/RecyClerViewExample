package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.mvp.presenter.BaseMvpPresenter;

public class MvpActivity extends BaseMvpActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
    }

    @Override
    protected BaseMvpPresenter createPresenter() {
        return null;
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle saveInstanceState) {

    }

    @Override
    public Dialog getDialog() {
        return null;
    }

    @Override
    public void cancelLoadDialog() {

    }
}
