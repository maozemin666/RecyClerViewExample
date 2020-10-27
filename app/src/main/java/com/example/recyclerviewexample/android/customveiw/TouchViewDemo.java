package com.example.recyclerviewexample.android.customveiw;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class TouchViewDemo extends LinearLayout {
    private static final String TAG = "maomaoTouchViewDemo";
    public TouchViewDemo(Context context) {
        this(context, null);
    }

    public TouchViewDemo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d(TAG, "dispatchTouchEvent: "+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onTouchEvent: "+ev.getAction());
        return super.onTouchEvent(ev);
    }
}
