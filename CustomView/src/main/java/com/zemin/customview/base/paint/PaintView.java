package com.zemin.customview.base.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Date 2020/7/8 22:37
 * @Created by zemin
 */
public class PaintView extends View {
    private Paint paint;
    private int width;
    private int height;

    public PaintView(Context context) {
        super(context);
        initPaint();
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        pathCategory(canvas);
    }

    private void pathCategory(Canvas canvas) {
//        pathFirtstCategory(canvas);
//        pathTwoCategory(canvas);
        pathThirdCategory(canvas);
    }

    private void pathFirtstCategory(Canvas canvas) {
        canvas.translate(width / 2, height / 2);
        Path path = new Path();
        path.lineTo(200, 200);
        path.lineTo(200, 0);
        path.close();
        canvas.drawPath(path, paint);
    }

    /**
     * // 第一类(基本形状)
     * // 圆形
     * public void addCircle (float x, float y, float radius, Path.Direction dir)
     * // 椭圆
     * public void addOval (RectF oval, Path.Direction dir)
     * // 矩形
     * public void addRect (float left, float top, float right, float bottom, Path.Direction dir)
     * public void addRect (RectF rect, Path.Direction dir)
     * // 圆角矩形
     * public void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
     * public void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
     */
    private void pathTwoCategory(Canvas canvas) {
        canvas.translate(width / 2, height / 2);
        Path path = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        path.setLastPoint(300, -300);
        canvas.drawPath(path, paint);
    }

    /**
     * // 第三类(addArc与arcTo)
     * // addArc
     * public void addArc (RectF oval, float startAngle, float sweepAngle)
     * // arcTo
     * public void arcTo (RectF oval, float startAngle, float sweepAngle)
     * public void arcTo (RectF oval, float startAngle, float sweepAngle, boolean forceMoveTo)
     */
    private void pathThirdCategory(Canvas canvas) {
        // 移动坐标系到屏幕中心
        canvas.translate(width / 2, height / 2);
        // <-- 注意 翻转y坐标轴
        canvas.scale(1, -1);

        Path path = new Path();
        path.lineTo(300,300);
        RectF rectF = new RectF(0,0,300,300);
//        path.addArc(rectF, 0, 270);
//        等价
        path.arcTo(rectF, 0,270, true);
        canvas.drawPath(path,paint);
    }

}
