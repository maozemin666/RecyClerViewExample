package com.example.recyclerviewexample.android.view;

import android.view.View;
import android.view.ViewGroup;

public interface InterItemView {

    View onCreateView(ViewGroup parent);

    void onBindView(View header);
}
