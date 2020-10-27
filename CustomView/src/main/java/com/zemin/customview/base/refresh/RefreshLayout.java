package com.zemin.customview.base.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class RefreshLayout extends LinearLayout {
    private static final String TAG = "RefreshLayout";

    private float y;
    private float yLast;
    private float yMove;
    private int i;
    private boolean isIntercept;

    private boolean isRefresh = false;
    private final Scroller scroller = new Scroller(getContext());

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        setOrientation(VERTICAL);
        ProgressBar progressBar = new ProgressBar(context);
        addView(progressBar, 0);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // View的内容(content)相对于View本身在水平或垂直方向的偏移量.(相反)
        scrollTo(0, getChildAt(0).getHeight());
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "***********************************dispatchTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "***********************************dispatchTouchEvent: ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "***********************************dispatchTouchEvent: ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "***********************************dispatchTouchEvent: ACTION_CANCEL");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * onInterceptTouchEvent方法对触屏事件的拦截处理需要和onTouchEvent方法配合使用。
     * <p>
     * down事件首先传递到onInterceptTouchEvent方法中
     * <p>
     * onInterceptTouchEvent返回false表示将down事件交由子View来处理；若某一层子View的onTouchEvent返回了true，
     * 后续的move、up等事件都将先传递到ViewGroup的onInterceptTouchEvent的方法，并继续层层传递下去，交由子View处理；
     * 若子View的onTouchEvent都返回了false，则down事件将交由该ViewGroup的onTouchEvent来处理；
     * 如果ViewGroup的onTouchEvent返回false，down传递给父ViewGroup，后续事件不再传递给该ViewGroup；
     * 如果ViewGroup的onTouchEvent返回true，后续事件不再经过该ViewGroup的onInterceptTouchEvent方法，直接传递给onTouchEvent方法处理
     * <p>
     * onInterceptTouchEvent返回ture，down事件将转交该ViewGroup的onTouchEvent来处理；若onTouchEvent返回true，
     * 后续事件将不再经过该ViewGroup的onInterceptTouchEvent方法，直接交由该ViewGroup的onTouchEvent方法处理；
     * 若onTouchEvent方法返回false，后续事件都将交由父ViewGroup处理，不再经过该ViewGroup的onInterceptTouchEvent方法和onTouchEvent方法
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        y = ev.getRawY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onInterceptTouchEvent: ACTION_DOWN");
                isIntercept = false;
                break;
            case MotionEvent.ACTION_MOVE:
                if (y - yLast > 0) {
                    isIntercept = true;
                } else {
                    isIntercept = false;
                }
                Log.d(TAG, "onInterceptTouchEvent: ACTION_MOVE isIntercept = " + isIntercept);
                break;
            case MotionEvent.ACTION_UP:
                isIntercept = false;
                Log.d(TAG, "onInterceptTouchEvent: ACTION_UP");
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "onInterceptTouchEvent: ACTION_CANCEL");
                break;
        }
        yLast = y;
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        //动画还没结束的时候，直接消耗掉事件,不处理。
        if (!scroller.isFinished() || isRefresh) {
            return true;
        }
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "============================onTouchEvent: ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "============================onTouchEvent: ACTION_MOVE");
                yMove = y - yLast;
                if (yMove >= 0) {
                    scrollBy(0, (int) (-yMove / 3));   //  /3为了让下拉有感觉
                    i += yMove / 3;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "============================onTouchEvent: ACTION_UP");
                if (i >= getChildAt(0).getHeight()) {
                    smoothToScroll(i - getChildAt(0).getHeight());
                    i = getChildAt(0).getHeight();
                    isRefresh = true;
                    Toast.makeText(getContext(), "正在刷新中", Toast.LENGTH_SHORT).show();
                } else {
                    endRefresh();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                Log.d(TAG, "============================onTouchEvent: ACTION_CANCEL");
                break;
        }
        yLast = y;
        return true;
    }

    private void smoothToScroll(int destaY) {
        scroller.startScroll(0, getScrollY(), 0, destaY, 500);
        invalidate();
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()) {
            scrollTo(0, scroller.getCurrY());
            postInvalidate();
        }
    }

    public void endRefresh() {
        isRefresh = false;
        smoothToScroll(i);
        i = 0;
    }
}