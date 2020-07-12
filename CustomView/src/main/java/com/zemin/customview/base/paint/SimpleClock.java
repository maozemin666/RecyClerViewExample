package com.zemin.customview.base.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Calendar;

/**
 *
 * save(): 用来保存Canvas的状态,save()方法之后的代码，可以调用Canvas的平移、放缩、旋转、裁剪等操作！
 * restore()：用来恢复Canvas之前保存的状态(可以想成是保存坐标轴的状态),防止save()方法代码之后对Canvas执行的操作，
 * 继续对后续的绘制会产生影响，通过该方法可以避免连带的影响
 *
 *  @Date 2020/7/11 23:11
 * @Created by zemin
 */
public class SimpleClock extends View {
    private static final String TAG = "SimpleClock";

    private Paint paint;
    private int radius = 300;
    private int width;
    private int height;
    private int circleWidth = 4;
    private int scaleMax = 50;
    private int scaleMin = 25;
    private int numberSpace = 10;
    // 时针宽度
    private int hourPointWidth = 15;

    // 分针宽度
    private int minutePointWidth = 10;

    // 秒针宽度
    private int secondPointWidth = 4;

    // 指针矩形的弧度
    private int pointRange = 20;


    public SimpleClock(Context context) {
        this(context, null);
    }

    public SimpleClock(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SimpleClock(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        paint = new Paint();
        paint.setTextSize(35f);
        paint.setTypeface(Typeface.DEFAULT_BOLD);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = onMeasuredSpec(widthMeasureSpec) + circleWidth * 2;
        height = onMeasuredSpec(heightMeasureSpec) + circleWidth * 2;

        radius = (width - circleWidth * 2) / 2;
        setMeasuredDimension(width, height);
    }

    public int onMeasuredSpec(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                result = Math.min(radius, specSize);
                break;
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int centerX = width / 2;
        int centerY = height / 2;
        canvas.translate(centerX, centerY);

        drawClock(canvas);
        drawClockScale(canvas);
        drawPointer(canvas);
        postInvalidateDelayed(1000);
    }

    private void drawClock(Canvas canvas) {
        paint.setStrokeWidth(circleWidth);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        canvas.drawCircle(0, 0, radius, paint);
    }

    private void drawClockScale(Canvas canvas) {
        for (int i = 1; i <= 60; i++) {
            canvas.rotate(6, 0, 0);
            if (i % 5 == 0) {
                paint.setStrokeWidth(4);
                canvas.drawLine(0, -radius, 0, -radius + scaleMax, paint);

                /** 绘制文本
                 *
                 * 1、平移 y 轴距离 = - 半径 + 刻度线长度 + 刻度与文本间距 + 文本高度 / 2
                 * （因为坐标原点在圆心，需要平移到12点钟位置，所以半径为负数）
                 * 2、旋转角度 = - 6 * 数字大小
                 * 3、文本 x 轴距离 = 文本宽度 / 2 ；
                 * 4、文本 y 轴距离 = 文本高度 / 2 ；
                 *
                 */
                canvas.save();
                paint.setStrokeWidth(1);
                paint.setStyle(Paint.Style.FILL);
                Rect textBunds = new Rect();
                String number = String.valueOf(i / 5);
                paint.getTextBounds(number, 0, number.length(), textBunds);
                canvas.translate(0, -radius + scaleMax + numberSpace + textBunds.height() / 2);
                canvas.rotate(-6 * i);
                canvas.drawText(number, -textBunds.width() / 2, textBunds.height() / 2, paint);
                canvas.restore();
            } else {
                paint.setStrokeWidth(2);
                canvas.drawLine(0, -radius, 0, -radius + scaleMin, paint);
            }
        }
    }

    /**
     * 首先获取当前时间
     * 根据当前时间计算指针旋转过的角度
     * 利用Canvas.rotate()旋转画布
     * 使用Canvas.drawRoundRect()绘制指针矩形
     * 绘制圆点
     *
     * @param canvas
     */
    private void drawPointer(Canvas canvas) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int angleHour = (hour + minute / 60) * 360 / 12;
        int angleMinute = (minute + second / 60) * 360 / 60;
        int angleSecond = second * 360 / 60;
        Log.d(TAG, "drawPointer: angleHour=" + angleHour + " hour=" + hour +
                " minute=" + minute + " second=" + second + " angleSecond=" + angleSecond);

        canvas.save();
        canvas.rotate(angleHour, 0, 0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(hourPointWidth);
        RectF hourRect = new RectF(-hourPointWidth / 2, -radius / 2, hourPointWidth / 2, radius / 6);
        canvas.drawRoundRect(hourRect, pointRange, pointRange, paint);
        canvas.restore();

        canvas.save();
        canvas.rotate(angleMinute, 0, 0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(minutePointWidth);
        RectF minRect = new RectF(-minutePointWidth / 2,
                -radius * 3.5f / 5,
                minutePointWidth / 2,
                radius / 6);
        canvas.drawRoundRect(minRect, pointRange, pointRange, paint);
        canvas.restore();

        canvas.save();
        canvas.rotate(angleSecond, 0, 0);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(secondPointWidth);
        RectF secRect = new RectF(-secondPointWidth / 2,
                -radius + 10,
                secondPointWidth / 2,
                radius / 6);
        canvas.drawRoundRect(secRect, pointRange, pointRange, paint);
        canvas.restore();

        // 绘制原点
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(
                0F,
                0F, secondPointWidth * 4, paint
        );
    }
}
