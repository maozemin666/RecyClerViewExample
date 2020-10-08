package com.example.recyclerviewexample.designmode.proxy;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.zemin.customview.base.banner.BannerImageViewHolder;
import com.zemin.customview.base.banner.demo.BannerBean;

/**
 * @Date 2020/10/8 21:13
 * @Created by zemin
 */
public class ImageProxy extends VarietyAdapter.Proxy<BannerBean, BannerImageViewHolder> {
    @Override
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    void onBindViewHolder(BannerImageViewHolder holder, BannerBean data, int position) {

    }
}
