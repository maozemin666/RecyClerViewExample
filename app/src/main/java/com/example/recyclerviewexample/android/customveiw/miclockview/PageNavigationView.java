package com.example.recyclerviewexample.android.customveiw.miclockview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

public class PageNavigationView extends ViewGroup {
    public PageNavigationView(Context context) {
        this(context, null);
    }

    public PageNavigationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public PageNavigationView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
