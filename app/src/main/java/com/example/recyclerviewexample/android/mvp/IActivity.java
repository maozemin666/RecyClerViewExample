package com.example.recyclerviewexample.android.mvp;

import android.os.Bundle;

public interface IActivity {
    int getLayout();

    void initView();

    void initData(Bundle saveInstanceState);
}
