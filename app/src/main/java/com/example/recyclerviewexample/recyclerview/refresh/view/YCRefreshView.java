package com.example.recyclerviewexample.recyclerview.refresh.view;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.recyclerviewexample.R;

public class YCRefreshView extends FrameLayout {
    private Context mContext;
    private LayoutInflater mInflater;
    private boolean mClipToPadding;
    private int mPadding;
    private int mPaddingRight;
    private int mPaddingLeft;
    private int mPaddingBottom;
    private int mPaddingTop;
    private int mScrollBarStyle;
    private int mScrollbar;
    private int mProgressId;
    private int mErrorId;
    private int mEmptyId;
    private SwipeRefreshLayout mPtrLayout;
    private ViewGroup mEmptyView;
    private ViewGroup mProgressView;
    private ViewGroup mErrorView;
    private RecyclerView mRecyclerView;

    public YCRefreshView(Context context) {
        this(context, null);
    }

    public YCRefreshView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YCRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        initAttrs(attrs);
        initView();
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.YCRefreshView);
        try {
            mClipToPadding = a.getBoolean(R.styleable.YCRefreshView_recyclerClipToPadding, false);
            mPadding = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPadding, -1.0f);
            mPaddingTop = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingTop, 0.0f);
            mPaddingBottom = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingBottom, 0.0f);
            mPaddingLeft = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingLeft, 0.0f);
            mPaddingRight = (int) a.getDimension(R.styleable.YCRefreshView_recyclerPaddingRight, 0.0f);
            mScrollBarStyle = a.getInteger(R.styleable.YCRefreshView_scrollBarStyle, -1);
            mScrollbar = a.getInteger(R.styleable.YCRefreshView_scrollbars, -1);
            mEmptyId = a.getResourceId(R.styleable.YCRefreshView_layout_empty, 0);
            mErrorId = a.getResourceId(R.styleable.YCRefreshView_layout_error, 0);
            mProgressId = a.getResourceId(R.styleable.YCRefreshView_layout_progress, 0);
        } finally {
            a.recycle();
        }
    }

    private void initView() {
        //使用isInEditMode解决可视化编辑器无法识别自定义控件的问题
        if (isInEditMode()) {
            return;
        }

        View view = mInflater.inflate(R.layout.refresh_recyclerview, this);
        mPtrLayout = view.findViewById(R.id.ptr_layout);
        mPtrLayout.setEnabled(true);

        mEmptyView = view.findViewById(R.id.empty);
        if (mEmptyId != 0) {
            mInflater.inflate(mEmptyId, mEmptyView);
        }

        mProgressView = view.findViewById(R.id.progress);
        if (mProgressId != 0) {
            mInflater.inflate(mProgressId, mProgressView);
        }

        mErrorView = view.findViewById(R.id.error);
        if (mErrorId != 0) {
            mInflater.inflate(mErrorId, mErrorView);
        }

        initRecyclerView(view);
    }

    private void initRecyclerView(View view) {
        mRecyclerView = view.findViewById(R.id.list);
        setItemAnimator(null);
        if (mRecyclerView != null) {
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setClipToPadding(mClipToPadding);
            if (mPadding != -1) {
                mRecyclerView.setPadding(mPadding, mPadding, mPadding, mPadding);
            } else {
                mRecyclerView.setPadding(mPaddingLeft, mPaddingTop, mPaddingRight, mPaddingBottom);
            }

            if (mScrollBarStyle != -1) {
                mRecyclerView.setScrollBarStyle(mScrollBarStyle);
            }

            switch (mScrollbar) {
                case 0:
                    setVerticalScrollBarEnabled(false);
                    break;
                case 1:
                    setHorizontalScrollBarEnabled(false);
                    break;
                case 2:
                    setVerticalScrollBarEnabled(false);
                    setHorizontalScrollBarEnabled(false);
                    break;
                default:
                    break;
            }
        }
    }

    public void setItemAnimator(RecyclerView.ItemAnimator itemAnimator) {
    }

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        if (linearLayoutManager != null) {
            mRecyclerView.setLayoutManager(linearLayoutManager);
        } else {
            throw new NullPointerException("un find manager");
        }
    }

    public void setAdapterWithProgress(RecyclerView.Adapter adapter) {
        mRecyclerView.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new ViewDataObserver());
        if (adapter instanceof RecyclerArrayAdapter) {
            if (((RecyclerArrayAdapter)adapter).getCount() == 0) {
                showProgress();
            } else {
                showRecycler();
            }
        } else {
            if (adapter.getItemCount() == 0) {
                showProgress();
            } else {
                showRecycler();
            }
        }
    }

    private void showProgress() {
        if (mProgressView.getChildCount() > 0) {
            hideAll();
            mProgressView.setVisibility(View.VISIBLE);
        } else {
            showRecycler();
        }
    }

    private void showRecycler() {
        hideAll();
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void hideAll() {
        mProgressView.setVisibility(GONE);
        mEmptyView.setVisibility(GONE);
        mErrorView.setVisibility(GONE);
        mPtrLayout.setRefreshing(false);
        mRecyclerView.setVisibility(INVISIBLE);
    }
}
