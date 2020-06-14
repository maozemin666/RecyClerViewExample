package com.example.recyclerviewexample.android.customveiw.amountview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.example.recyclerviewexample.R;

public class AmountView extends View {
    private static final String TAG = "AmountView";

    private static final int MAX_AMOUNT = 3000000;
    private int amount = MAX_AMOUNT;
    private String hintText = "最高可借金额";
    private int shadowColor = Color.parseColor("1E90FF");
    private int alphaShadowColor = Color.parseColor("#331E90FF");
    private int mMinWidth = 160;
    private int mMinHeight = 80;
    private int strokeWidth;
    private int shadowOffset;

    private Path radianPath;
    private Paint radianPaint;
    private int shadowStrokeWidth;
    private int hintOffset;
    private Path shadowPath;
    private Paint shadowPaint;
    private Paint amountPaint;
    private Paint hintPaint;
    private Paint progressPaint;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public AmountView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initArgs(context, attrs);
    }

    private void initArgs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AmountView);
        amount = typedArray.getInt(R.styleable.AmountView_max_amount, MAX_AMOUNT);
        hintText = typedArray.getString(R.styleable.AmountView_hint_text);
        if (TextUtils.isEmpty(hintText)) {
            hintText = context.getString(R.string.default_hint_amount);
        }
        shadowColor = typedArray.getColor(R.styleable.AmountView_shadow_color, shadowColor);
        //透明度33
        alphaShadowColor = Color.argb(16 * 3 + 3, Color.red(shadowColor), Color.green(shadowColor), Color.blue(shadowColor));
        Log.d(TAG, "initArgs: ");

        shadowOffset = dp2Px(7);
        strokeWidth = dp2Px(4);
        shadowStrokeWidth = dp2Px(3);
        hintOffset = dp2Px(3);
        mMinWidth = dp2Px(mMinWidth);
        mMinHeight = dp2Px(mMinHeight);

        radianPath = new Path();
        radianPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        radianPaint.setColor(alphaShadowColor);
        radianPaint.setStrokeWidth(strokeWidth);
        radianPaint.setStyle(Paint.Style.STROKE);

        shadowPath = new Path();
        shadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        shadowPaint.setStrokeWidth(shadowStrokeWidth);
        shadowPaint.setStyle(Paint.Style.STROKE);
        //设置自定义填充效果和笔触效果（DashPathEffect 用于控制虚线显示长度和隐藏长度，它必须为偶数(且至少为 2 个)）
        shadowPaint.setPathEffect(new DashPathEffect(new float[]{dp2Px(1.5f), dp2Px(4)}, 0));

        amountPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        amountPaint.setColor(Color.BLACK);
        amountPaint.setTextSize(dp2Sp(20));
        amountPaint.setTextAlign(Paint.Align.CENTER);
        
        hintPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        hintPaint.setColor(Color.BLACK);
        hintPaint.setTextSize(dp2Sp(10));
        hintPaint.setTextAlign(Paint.Align.CENTER);
        
        progressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(strokeWidth);
        
        initAnimator();
    }

    private void initAnimator() {
        
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        mMinWidth = mMinWidth + getPaddingLeft() + getPaddingRight();
        mMinHeight = mMinHeight + getPaddingTop() + getPaddingBottom();
        Log.d(TAG, "getPaddingLeft: " + getPaddingLeft());
        if (widthMode == MeasureSpec.AT_MOST) {
            if (heightMode == MeasureSpec.EXACTLY) {
                width = (height - getPaddingTop() - getPaddingBottom()) * 2 + getPaddingLeft() + getPaddingRight();
            } else {
                width = mMinWidth;
                height = mMinHeight;
            }
        } else if (widthMode == MeasureSpec.EXACTLY) {
            if (heightMode == MeasureSpec.EXACTLY) {
                width = Math.min((height - getPaddingTop() - getPaddingBottom()) * 2, width - getPaddingStart() - getPaddingRight());
                height = (int) (width * 1.0 / 2) + getPaddingTop() + getPaddingBottom();
                width = width + getPaddingStart() + getPaddingRight();
            } else {
                height = (width - getPaddingStart() - getPaddingRight()) / 2 + getPaddingTop() + getPaddingBottom();
            }
        } else {
            width = mMinWidth;
            height = mMinHeight;
        }

        radianPath.addCircle(width / 2, height - getPaddingBottom()
                , height - getPaddingTop() - getPaddingBottom() - strokeWidth, Path.Direction.CW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawCircle(canvas);
        drawAmountText(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawPath(radianPath, radianPaint);
    }

    private void drawAmountText(Canvas canvas) {

    }

    private int dp2Px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics());
    }

    private int dp2Sp(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value, getResources().getDisplayMetrics());
    }
}
