package com.zemin.customview.base.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Arrays;

/**
 * 设M为原始矩阵，S为变换矩阵，M'为变换后的矩阵，
 * pre(S)相当于矩阵的右乘：M' = M • S
 * post(S)相当于矩阵的左乘：M' = S • M
 *
 *
 * @Date 2020/7/11 18:05
 * @Created by zemin
 */
public class MatrixBase extends View {
    private static final String TAG = MatrixBase.class.getSimpleName();

    public MatrixBase(Context context) {
        this(context, null);
    }

    public MatrixBase(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mapPoints(canvas);
    }

    private void mapPoints(Canvas canvas) {
//        mapPointsBase1();
        mapPointsBase2();
    }

    private void mapPointsBase1() {
        float[] pts = new float[]{0, 0, 80, 100, 400, 300};
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 1);

        Log.d(TAG, "mapPoints: before pts=" + Arrays.toString(pts));
        matrix.mapPoints(pts);
        Log.d(TAG, "mapPoints: after pts=" + Arrays.toString(pts));
    }

    /**
     *  float[] dst, int dstIndex, float[] src, int srcIndex, int pointCount
     */
    private void mapPointsBase2() {
        float[] src = new float[]{0, 0, 80, 100, 400, 300};
        float[] dst = new float[]{0, 0, 0, 0, 0, 0};
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f,1);

        Log.d(TAG, "mapPoints: before pts=" + Arrays.toString(dst));
        matrix.mapPoints(dst, 0, src, 2, 2);
        Log.d(TAG, "mapPoints: after pts=" + Arrays.toString(dst));
    }
}
