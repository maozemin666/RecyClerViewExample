package com.zemin.customview.base.banner;

import android.view.View;

/**
 * @Date 2020/9/14 21:32
 * @Created by zemin
 */
public interface ItemViewHolder<T, VH> {
    VH onCreateHolder(View parent, int viewType);

    void onBindView(VH holder, T data);
}
