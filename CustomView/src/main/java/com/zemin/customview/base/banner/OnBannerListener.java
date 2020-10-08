package com.zemin.customview.base.banner;

/**
 * @Date 2020/9/14 21:44
 * @Created by zemin
 */
public interface OnBannerListener<T> {
    void onBannerClick(T data, int position);
}
