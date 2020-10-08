package com.zemin.customview.base.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.lang.ref.WeakReference;

/**
 * @Date 2020/9/14 14:43
 * @Created by zemin
 */
public class Banner<BA extends BannerAdapter> extends FrameLayout {
    private static final String TAG = "Banner";

    private ViewPager2 viewPager2;

    private BannerOnPageChangeCallback bannerOnPageChangeCallback;

    private CompositePageTransformer compositePageTransformer;

    private boolean isAutoLoop = BannerConfig.IS_AUTO_LOOP;

    private BA adapter;

    private AutoLoopTask autoLoopTask;

    private long loopTime = BannerConfig.LOOP_TIME;

    private int startPosition = 1;

    private boolean isIntercept;

    private float startX;

    private float startY;

    private boolean isViewPage2Drag;

    private int touchSlop;

    private RecyclerView.AdapterDataObserver adapterDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            if (getItemCount() <= 1) {
                stop();
            } else {
                start();
            }
        }
    };

    public Banner(@NonNull Context context) {
        this(context, null);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop() / 2;
        bannerOnPageChangeCallback = new BannerOnPageChangeCallback();
        compositePageTransformer = new CompositePageTransformer();
        autoLoopTask = new AutoLoopTask(this);
        viewPager2 = new ViewPager2(context);
        viewPager2.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        viewPager2.setOffscreenPageLimit(1);
        viewPager2.registerOnPageChangeCallback(bannerOnPageChangeCallback);
        viewPager2.setPageTransformer(compositePageTransformer);
        addView(viewPager2);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!getViewPager2().isUserInputEnabled()) {
            return super.dispatchTouchEvent(ev);
        }
        int action = ev.getActionMasked();
        if (action == MotionEvent.ACTION_DOWN) {
            stop();
        } else if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE) {
            start();
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!getViewPager2().isUserInputEnabled() || !isIntercept) {
            return super.onInterceptTouchEvent(ev);
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = ev.getX();
                startY = ev.getY();
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_MOVE:
                float endX = ev.getX();
                float endY = ev.getY();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                if (getViewPager2().getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                    isViewPage2Drag = distanceX > touchSlop && distanceX > distanceY;
                } else {
                    isViewPage2Drag = distanceY > touchSlop && distanceY > distanceX;
                }
                getParent().requestDisallowInterceptTouchEvent(isViewPage2Drag);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void setIntercept(boolean intercept) {
        isIntercept = intercept;
    }

    public Banner setAdapter(BA adapter) {
        if (adapter == null) {
            throw new NullPointerException("null adapter");
        }
        this.adapter = adapter;
        viewPager2.setAdapter(adapter);
        this.adapter.registerAdapterDataObserver(adapterDataObserver);
        setCurrentItem(startPosition);
        return this;
    }

    public Banner start() {
        if (isAutoLoop) {
            stop();
            postDelayed(autoLoopTask, loopTime);
        }
        return this;
    }

    public Banner stop() {
        if (isAutoLoop) {
            removeCallbacks(autoLoopTask);
        }
        return this;
    }

    public void destroy() {
        stop();
        if (adapter != null) {
            adapter.unregisterAdapterDataObserver(adapterDataObserver);
        }
    }

    public int getItemCount() {
        if (getAdapter() == null) {
            return 0;
        }
        return getAdapter().getItemCount();
    }

    private BA getAdapter() {
        if (adapter == null) {
            Log.e(TAG, "getAdapter: adapter is null");
        }
        return adapter;
    }

    public void setCurrentItem(int next) {
        getViewPager2().setCurrentItem(next);
    }

    public int getCurrentItem() {
        return getViewPager2().getCurrentItem();
    }

    public ViewPager2 getViewPager2() {
        return viewPager2;
    }


    class BannerOnPageChangeCallback extends ViewPager2.OnPageChangeCallback {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            super.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }
    }

    static class AutoLoopTask implements Runnable {
        private final WeakReference<Banner> reference;

        AutoLoopTask(Banner banner) {
            reference = new WeakReference<>(banner);
        }

        @Override
        public void run() {
            Banner banner = reference.get();
            if (banner != null && banner.isAutoLoop) {
                int count = banner.getItemCount();
                if (count == 0) {
                    return;
                }
                int next = (banner.getCurrentItem() + 1) % count;
                banner.setCurrentItem(next);
                banner.postDelayed(this, banner.loopTime);
            }
        }

    }

}
