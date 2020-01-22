package com.example.recyclerviewexample.recyclerview.refresh.view;

import android.view.View;
import android.view.ViewGroup;

public interface InterItemView {

    View onCreateView(ViewGroup parent);

    void onBindView(View header);
}
