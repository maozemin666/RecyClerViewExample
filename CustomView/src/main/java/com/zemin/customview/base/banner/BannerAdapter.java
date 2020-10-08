package com.zemin.customview.base.banner;

import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date 2020/9/14 20:50
 * @Created by zemin
 */
public abstract class BannerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements ItemViewHolder<T, VH> {
    private static final String TAG = "BannerAdapter";

    protected List<T> datas = new ArrayList<>();

    private OnBannerListener onBannerListener;

    public BannerAdapter(List<T> datas) {
        setDatas(datas);
    }

    public T getData(int position) {
        return datas.get(position);
    }

    public void setDatas(List<T> datas) {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        this.datas = datas;
    }

    @NonNull
    @Override
    public final VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        return onCreateHolder(parent, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull VH holder, int position) {
        Log.d(TAG, "onBindViewHolder: ");
        onBindView(holder, getData(position));
        final int real = position;
        if (onBannerListener != null) {
            holder.itemView.setOnClickListener(v -> onBannerListener.onBannerClick(getData(real), real));
        }
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setOnBannerListener(OnBannerListener onBannerListener) {
        this.onBannerListener = onBannerListener;
    }
}
