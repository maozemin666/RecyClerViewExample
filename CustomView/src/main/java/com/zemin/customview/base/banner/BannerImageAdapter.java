package com.zemin.customview.base.banner;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * @Date 2020/9/14 21:50
 * @Created by zemin
 */
public abstract class BannerImageAdapter<T> extends BannerAdapter<T, BannerImageViewHolder> {
    public BannerImageAdapter(List<T> datas) {
        super(datas);
    }

    @Override
    public BannerImageViewHolder onCreateHolder(View parent, int viewType) {
        ImageView imageView = new ImageView(parent.getContext());
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return new BannerImageViewHolder(imageView);
    }
}
