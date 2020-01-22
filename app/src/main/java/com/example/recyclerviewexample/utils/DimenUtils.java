package com.example.recyclerviewexample.utils;

import android.content.Context;

public class DimenUtils {

    private DimenUtils() {
    }

    public static int sp2Px(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (scaledDensity * sp + 0.5f);
    }
}
