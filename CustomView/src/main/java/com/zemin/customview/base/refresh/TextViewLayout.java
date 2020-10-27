package com.zemin.customview.base.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class TextViewLayout extends TextView {
    private static final String TAG = "TextViewLayout";
    public TextViewLayout(Context context) {
        this(context, null);
    }

    public TextViewLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& dispatchTouchEvent: ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.e(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& dispatchTouchEvent: ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.w(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.w(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& onTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.w(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& onTouchEvent: ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.w(TAG, "&&&&&&&&&&&&&&&&&&&&&&&&&&& onTouchEvent: ACTION_CANCEL");
                break;
        }
        return super.onTouchEvent(ev);
    }
}
