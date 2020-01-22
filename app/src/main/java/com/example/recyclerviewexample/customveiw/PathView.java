package com.example.recyclerviewexample.customveiw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class PathView extends View {

    private int mHeight;
    private int mWidth;

    private Paint mPaint = new Paint();

    public PathView(Context context) {
        this(context,null);
    }

    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mHeight = h;
        mWidth = w;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawPath1(canvas);
//        drawPath2(canvas);
//        drawPath3(canvas);
        
//        drawBooleanPath(canvas);
//        drawCalculateBoundsPath(canvas);
        
        pathMeasure(canvas);
    }

    private void pathMeasure(Canvas canvas) {

    }

    private void drawCalculateBoundsPath(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        path.lineTo(100,-50);
        path.lineTo(100,50);
        path.close();
        path.addCircle(-100,0,100, Path.Direction.CW);

        RectF rectF = new RectF();
        path.computeBounds(rectF,true); //计算Path所占用的空间以及所在位置并存入rectF

        canvas.drawPath(path,mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        canvas.drawRect(rectF,mPaint);
    }

    private void drawBooleanPath(Canvas canvas) {
        int x = 80;
        int r = 100;
        canvas.translate(250,0);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(50);

        Path path1 = new Path();
        Path path2 = new Path();
        Path pathOpResult = new Path();

        path1.addCircle(-x,0,r, Path.Direction.CW);
        path2.addCircle(x,0,r, Path.Direction.CW);

        pathOpResult.op(path1,path2, Path.Op.DIFFERENCE);
        canvas.translate(0,200);
        canvas.drawText("DIFFERENCE",240,0,mPaint);

        pathOpResult.op(path1,path2, Path.Op.REVERSE_DIFFERENCE);
        canvas.translate(0,300);
        canvas.drawText("REVERSE_DIFFERENCE",240,0,mPaint);

        pathOpResult.op(path1,path2, Path.Op.INTERSECT);
        canvas.translate(0,300);
        canvas.drawText("INTERSECT",240,0,mPaint);

        pathOpResult.op(path1,path2, Path.Op.UNION);
        canvas.translate(0,300);
        canvas.drawText("UNION",240,0,mPaint);

        pathOpResult.op(path1,path2, Path.Op.XOR);
        canvas.translate(0,300);
        canvas.drawText("XOR",240,0,mPaint);

        canvas.drawPath(pathOpResult,mPaint);

    }

    private void drawPath1(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        canvas.scale(1,-1);  //y轴翻转

        Path path = new Path();
        RectF rectF = new RectF(-200,-200,200,200);
        path.addRect(rectF,Path.Direction.CW);

        Path src = new Path();
        src.addCircle(0,0,100, Path.Direction.CW);

        path.addPath(src,0,200);
        canvas.drawPath(path,mPaint);
    }

    private void drawPath2(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        RectF rectF = new RectF(-200,-200,200,200);
//        path.addRect(rectF, Path.Direction.CW);
        path.addRect(rectF, Path.Direction.CCW);

        RectF rectF2 = new RectF(-400,-400,400,400);
        path.addRect(rectF2, Path.Direction.CW);

        path.setFillType(Path.FillType.EVEN_ODD); // 设置Path填充模式为非零环绕规则
//        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);
        canvas.drawPath(path,mPaint);
    }

    private void drawPath3(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStyle(Paint.Style.FILL);

        Path path = new Path();
        RectF rectF = new RectF(-200,-200,200,200);
        path.addRect(rectF, Path.Direction.CW);
//        path.addRect(rectF, Path.Direction.CCW);

//        path.setFillType(Path.FillType.EVEN_ODD);  //设置Path填充模式为 奇偶规则
        path.setFillType(Path.FillType.INVERSE_EVEN_ODD);  //设置Path填充模式为反奇偶规则
        canvas.drawPath(path,mPaint);
    }
}
