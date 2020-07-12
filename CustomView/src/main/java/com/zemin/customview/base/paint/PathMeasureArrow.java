package com.zemin.customview.base.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.zemin.customview.R;

/**
 * @Date 2020/7/11 10:36
 * @Created by zemin
 */
public class PathMeasureArrow extends View {
    private static final String TAG = PathMeasureArrow.class.getSimpleName();
    private float[] pos;
    private float[] tan;
    private Matrix matrix;
    private Bitmap arrow;
    private int width;
    private int height;
    private float currentValue;
    private Paint paint;

    public PathMeasureArrow(Context context) {
        super(context);
        init(context);
    }

    public PathMeasureArrow(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);

        pos = new float[2];
        tan = new float[2];
        matrix = new Matrix();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        arrow = BitmapFactory.decodeResource(context.getResources(), R.drawable.arrow, options);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        pathMeasureOfgetPosTan(canvas);
        pathMeasureOfgetMatrix(canvas);
    }

    private void pathMeasureOfgetPosTan(Canvas canvas) {
        canvas.translate(width / 2, height / 2);
        Path circle = new Path();
        circle.addCircle(0, 0, 200, Path.Direction.CW);

        currentValue += 0.005;
        if (currentValue >= 1) {
            currentValue = 0;
        }

        PathMeasure measure = new PathMeasure(circle, false);
        measure.getPosTan(measure.getLength() * currentValue, pos, tan);

        matrix.reset();
        // 计算图片旋转角度 tan[0] = x = 0， tan[1] = y = 1
        float degree = (float) ((Math.atan2(tan[1], tan[0]) * 180) / Math.PI);
        Log.d(TAG, "onDraw: degree=" + degree);

        // 计算图片旋转角度,旋转的中心点
        matrix.postRotate(degree, arrow.getWidth() / 2, arrow.getHeight() / 2);
        //参考点为坐标原点（0，0）移动到（pos[0]-width/2,pos[1] -heigth/2），将图片绘制中心调整到与当前点重合
        matrix.postTranslate(pos[0] - arrow.getWidth() / 2, pos[1] - arrow.getHeight() / 2);

        canvas.drawPath(circle, paint);
        canvas.drawBitmap(arrow, matrix, paint);
        invalidate();
    }

    private void pathMeasureOfgetMatrix(Canvas canvas) {
        canvas.translate(width / 2, height / 2);
        Path circle = new Path();
        circle.addCircle(0, 0, 200, Path.Direction.CW);

        currentValue += 0.005;
        if (currentValue >= 1) {
            currentValue = 0;
        }

        PathMeasure measure = new PathMeasure(circle, false);
        measure.getMatrix(measure.getLength() * currentValue, matrix,
                PathMeasure.POSITION_MATRIX_FLAG|PathMeasure.TANGENT_MATRIX_FLAG);
        // <-- 将图片绘制中心调整到与当前点重合(注意:此处是前乘pre)
        matrix.preTranslate(-arrow.getWidth() / 2, -arrow.getHeight() / 2);

        canvas.drawPath(circle, paint);
        canvas.drawBitmap(arrow, matrix, paint);
        invalidate();
    }
}
