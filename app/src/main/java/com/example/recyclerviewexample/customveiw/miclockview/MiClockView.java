package com.example.recyclerviewexample.customveiw.miclockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

public class MiClockView extends View {
    private float mRadius;
    private float mDefaultPadding;
    private float mPaddingLeft;
    private float mPaddingRight;
    private float mPaddingTop;
    private float mPaddingBottom;
    private float mScaleLength;
    private Paint mScaleArcPaint;
    private Paint mScaleLinePaint;
    private SweepGradient mSweepGradient;
    private int mDarkColor;
    private int mLightColor;
    private Canvas mCanvas;
    private float mSecondDegree;
    private float mHourDegree;
    private float mMinuteDegree;

    public MiClockView(Context context) {
        super(context, null);
    }

    public MiClockView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MiClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MiClockView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureDimension(widthMeasureSpec), measureDimension(heightMeasureSpec));
    }

    private int measureDimension(int widthMeasureSpec) {
        int result = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            case MeasureSpec.UNSPECIFIED:
                result = 800;
                break;
            case MeasureSpec.AT_MOST:
                result = 800;
                result = Math.min(result, size);
                break;
        }
        return result;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = Math.min(w - getPaddingLeft() - getPaddingRight(), h - getPaddingTop() - getPaddingBottom()) / 2;
        mDefaultPadding = mRadius * 0.12f;
        mPaddingLeft = mDefaultPadding + w/2 - mRadius + getPaddingLeft();
        mPaddingTop = mDefaultPadding + h/2 - mRadius + getPaddingTop();
        mPaddingRight = mPaddingLeft;
        mPaddingBottom = mPaddingTop;
        mScaleLength = 0.12f * mRadius;
        mScaleArcPaint.setStrokeWidth(mScaleLength);    //刻度盘的弧宽
        mScaleLinePaint.setStrokeWidth(0.012f * mRadius);   //刻度线的宽度
        mSweepGradient = new SweepGradient(w / 2, h / 2,
                new int[]{mDarkColor, mLightColor}, new float[]{0.75f, 1});
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mCanvas= canvas;
        getTimeDegree();
        drawTimeText();
    }

    private void drawTimeText() {
        String timeText = "12";
    }

    private void getTimeDegree() {
        Calendar calendar = Calendar.getInstance();
        float millisecond = calendar.get(Calendar.MILLISECOND);
        float second = calendar.get(Calendar.SECOND) + millisecond / 1000;
        float minute = calendar.get(Calendar.MINUTE) + second / 60;
        float hour = calendar.get(Calendar.HOUR) + minute / 60;
        mSecondDegree = millisecond / 60 * 360;
        mMinuteDegree = minute / 60 * 360;
        mHourDegree = hour / 12 * 360;
    }
}
