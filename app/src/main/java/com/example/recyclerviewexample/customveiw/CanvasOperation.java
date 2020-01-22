package com.example.recyclerviewexample.customveiw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class CanvasOperation extends View {

    private static final String DRAW_TEXT = "ABCDEFG";

    private int mHeight;
    private int mWidth;
    private Paint mPaint = new Paint();

    public CanvasOperation(Context context) {
        this(context,null);
    }

    public CanvasOperation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setAntiAlias(true);
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
//        translateCanvas(canvas);
//        canvasScale(canvas);
//        rotateCanvas(canvas);
//        rotateCanvas2(canvas);

//        绘制图片和文字
//        drawPicture(canvas);
        drawText1(canvas);
    }

    private void drawText1(Canvas canvas) {
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(50);

//         参数分别为 (文本 基线x 基线y 画笔)
//        canvas.drawText(DRAW_TEXT,200,200,textPaint);

// 参数分别为 (字符串 开始截取位置 结束截取位置 基线x 基线y 画笔)
//        canvas.drawText(DRAW_TEXT,1,3,200,200,textPaint);

        char[] chars = DRAW_TEXT.toCharArray();
        // 参数为 (字符数组 起始坐标 截取长度 基线x 基线y 画笔)
        canvas.drawText(chars,1,3,200,200,textPaint);
    }

    private void drawPicture(Canvas canvas) {
        Picture picture = new Picture();
        recording(picture);
//        canvas.drawPicture(picture);
        canvas.drawPicture(picture,new RectF(0,0,picture.getWidth(),picture.getHeight()/2));
    }

    private void recording(Picture picture) {
        Canvas canvas = picture.beginRecording(400,400);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);

        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);

        picture.endRecording();
    }

    private void rotateCanvas2(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        canvas.drawCircle(0,0,380,mPaint);
        canvas.drawCircle(0,0,400,mPaint);
        for (int i = 0; i < 360 ; i+=90) {
            canvas.drawLine(0,-380,0,-400,mPaint);
            canvas.rotate(90);
        }
    }

    private void rotateCanvas(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        RectF rectF = new RectF(0,-400,400,0);
        canvas.drawRect(rectF,mPaint);

        mPaint.setColor(Color.BLUE);
        canvas.rotate(90,200,0);
        canvas.drawRect(rectF,mPaint);
    }

    private void translateCanvas(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.BLACK);
        canvas.translate(100,100);
        canvas.drawCircle(0,0,100,mPaint);

//        两次移动是可叠加的。两次移动是可叠加的。
        mPaint.setColor(Color.BLUE);
        canvas.translate(200,200);
        canvas.drawCircle(0,0,100,mPaint);
        canvas.save();
    }

    private void canvasScale(Canvas canvas) {
        canvas.translate(mWidth/2,mHeight/2);
        mPaint.setStyle(Paint.Style.STROKE);

        mPaint.setColor(Color.BLACK);
        RectF rectF = new RectF(0,-400,400,0);
        canvas.drawRect(rectF,mPaint);
        canvas.scale(0.5f,0.5f,200,0);
//        canvas.scale(0.5f,0.5f,200,0);//缩放中心向右移动200

//        canvas.scale(-0.5f,-0.5f); //根据坐标中心,对画布进行翻转

//        调用两次缩放则 x轴实际缩放为0.5x0.5=0.25 y轴实际缩放为0.5x0.1=0.05
        canvas.scale(0.5f,0.5f);


        mPaint.setColor(Color.BLUE);
        canvas.drawRect(rectF,mPaint);
    }
}
