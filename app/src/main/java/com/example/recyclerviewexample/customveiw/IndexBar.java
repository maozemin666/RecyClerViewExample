package com.example.recyclerviewexample.customveiw;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.utils.DimenUtils;

import java.util.List;

public class IndexBar extends View {
    private static final String TAG = "maomaoIndexBar";

    public interface OnLetterChangeListener {
        void onLetterChange(int position, String letter);
    }

    private static final int[] STATE_FOCUSED = new int[]{android.R.attr.state_focused};

    private final int mNormColor;
    private final int mSelectColor;
    private final float mIndexSize;
    private Paint mPaint;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mHeight;
    private float mCellHeight;

    private List<String> letters;
    private float beginY;
    private int mCellWidth;
    private Rect mRect;
    private int mIndex = -1;
    private boolean press;


    private OnLetterChangeListener mOnLetterChangeListener;

    public IndexBar(Context context) {
        this(context, null);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public IndexBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        final TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IndexBar,defStyleAttr,defStyleRes);
        mNormColor = a.getColor(R.styleable.IndexBar_normColor, Color.GRAY);
        mSelectColor = a.getColor(R.styleable.IndexBar_selectColor, Color.BLUE);
        mIndexSize = a.getDimensionPixelSize(R.styleable.IndexBar_indexSize, DimenUtils.sp2Px(getContext(), 14));
        a.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mNormColor);
        mPaint.setTextSize(mIndexSize);
        mPaint.setTypeface(Typeface.DEFAULT);
        mRect = new Rect();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mPaddingTop = getPaddingTop();
        mPaddingBottom = getPaddingBottom();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.d(TAG, "layout: ");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: ");
        mHeight = getMeasuredHeight() - mPaddingTop - mPaddingBottom;
        mCellWidth = getMeasuredWidth();
        mCellHeight = mHeight * 0.1f / 26;
        if (letters != null) {
            beginY = (mHeight - letters.size() * mCellHeight) * 0.5f;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Log.d(TAG, "onDraw: ");
        if (letters != null) {
            for (int i = 0; i < letters.size(); i++) {
                String letter = letters.get(i);
                float textWidth = mPaint.measureText(letter);
                mPaint.getTextBounds(letter, 0, letter.length(), mRect);
                float textHeight = mRect.height();

                float x = (mCellWidth - textWidth) *0.5f;
                float y = textHeight *0.5f + mCellHeight *0.5f + i * mCellHeight + mPaddingTop + beginY;

                mPaint.setColor(mIndex == i ? mSelectColor : mNormColor);
                canvas.drawText(letter, x, y, mPaint);
            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean  onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float y;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                refreshState(true);
                y = event.getY();
                checkIndex(y);
                break;
            case MotionEvent.ACTION_MOVE:
                y = event.getY();
                checkIndex(y);
                break;
            case MotionEvent.ACTION_UP:
                refreshState(false);
                mIndex = -1;
                break;
        }
        invalidate();
        return true;
    }

    private void checkIndex(float y) {
        if (y < mPaddingTop + beginY) {
            return;
        }
        int currentIndex = (int) ((y - mPaddingTop - beginY) / mCellHeight);
        if (currentIndex != mIndex) {
            if (mOnLetterChangeListener != null) {
                if (letters != null && currentIndex < letters.size()) {
                    mOnLetterChangeListener.onLetterChange(currentIndex, letters.get(currentIndex));
                    mIndex = currentIndex;
                }
            }
        }
    }

    private void refreshState(boolean state) {
        if (press != state) {
            press = state;
        }
        refreshDrawableState();
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] state = super.onCreateDrawableState(extraSpace + 1);
        if (press) {
            mergeDrawableStates(state, STATE_FOCUSED);
        }
        return state;
    }

    public OnLetterChangeListener getOnLetterChangeListener() {
        return mOnLetterChangeListener;
    }

    public void setOnLetterChangeListener(OnLetterChangeListener mOnLetterChangeListener) {
        this.mOnLetterChangeListener = mOnLetterChangeListener;
    }

    public List<String> getLetters() {
        return letters;
    }

    public void setLetters(List<String> letters) {
        if (letters == null) {
            setVisibility(GONE);
            return;
        }
        this.letters = letters;
        mHeight = getMeasuredHeight()-mPaddingTop-mPaddingBottom;
        mCellWidth = getMeasuredWidth();
        mCellHeight = mHeight * 1.0f / 26;
        beginY = (mHeight - mCellHeight * letters.size()) * 0.5f;
        invalidate();
    }
}
