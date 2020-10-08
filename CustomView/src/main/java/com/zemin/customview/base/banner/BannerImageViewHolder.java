package com.zemin.customview.base.banner;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @Date 2020/9/14 21:54
 * @Created by zemin
 */
public class BannerImageViewHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;

    public BannerImageViewHolder(@NonNull View itemView) {
        super(itemView);
        this.imageView = (ImageView) itemView;
    }
}
