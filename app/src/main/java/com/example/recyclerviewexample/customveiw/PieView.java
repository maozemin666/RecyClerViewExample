package com.example.recyclerviewexample.customveiw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PieView extends View {

    // 颜色表 (注意: 此处定义颜色使用的是ARGB，带Alpha通道的)
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};

    private float mStartAngle = 0;
    private List<PieData> mData;
    private int mHeight;
    private int mWidth;
    private Paint mPaint = new Paint();

    public PieView(Context context) {
        this(context,null);
    }

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
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
        if (mData == null) {
            return;
        }

        float currentAngle = mStartAngle;
        canvas.translate(mWidth/2,mHeight/2);  // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth,mHeight)/2 * 0.8);
        RectF rectF = new RectF(-r,-r,r,r);     //绘制饼状图的区域

        for (PieData data : mData) {
            mPaint.setColor(data.getColor());
            canvas.drawArc(rectF,currentAngle,data.getAngle(),true,mPaint);
            currentAngle += data.getAngle();
        }
    }

    // 设置起始角度
    public void setStartAngle(int mStartAngle) {
        this.mStartAngle = mStartAngle;
        invalidate();   // 刷新
    }

    // 设置数据
    public void setData(ArrayList<PieData> mData) {
        this.mData = mData;
        initData(mData);
        invalidate();   // 刷新
    }

    private void initData(ArrayList<PieData> mData) {
        if (mData == null || mData.size() == 0) {
            return;
        }

        float sumValue = 0;
        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);
            int color = i % mColors.length;
            pieData.setColor(mColors[color]);
            sumValue += pieData.getValue();
        }

        for (int i = 0; i < mData.size(); i++) {
            PieData pieData = mData.get(i);

            float percentage = pieData.getValue() / sumValue;
            pieData.setPercentage(percentage);
            pieData.setAngle(percentage * 360);
        }
    }


    public static class PieData {
        // 用户关心数据
        private String name;        // 名字
        private float value;        // 数值
        private float percentage;   // 百分比

        // 非用户关心数据
        private int color = 0;      // 颜色
        private float angle = 0;    // 角度

        public PieData(@NonNull String name, @NonNull float value) {
            this.name = name;
            this.value = value;
        }

        public int getColor() {
            return color;
        }

        public void setColor(int color) {
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }

        public float getValue() {
            return value;
        }

        public void setValue(float value) {
            this.value = value;
        }

        public float getPercentage() {
            return percentage;
        }

        public void setPercentage(float percentage) {
            this.percentage = percentage;
        }
    }


}
