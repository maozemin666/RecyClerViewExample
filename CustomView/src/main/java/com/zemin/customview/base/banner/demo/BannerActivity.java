package com.zemin.customview.base.banner.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.zemin.customview.R;
import com.zemin.customview.base.banner.Banner;
import com.zemin.customview.base.banner.BannerImageAdapter;
import com.zemin.customview.base.banner.BannerImageViewHolder;

import java.util.ArrayList;
import java.util.List;

public class BannerActivity extends AppCompatActivity {
    private static final String TAG = "BannerActivity";

    private RecyclerView bannerContent;

    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        initView();
        loadData();

        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        banner.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        banner.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        banner.destroy();
    }

    private void initView() {
        bannerContent = findViewById(R.id.rv_banner);
        banner = findViewById(R.id.banner);
    }

    private void loadData() {
        banner.setAdapter(new BannerImageAdapter<BannerBean>(loadBannerData()) {
            @Override
            public void onBindView(BannerImageViewHolder holder, BannerBean data) {
                holder.imageView.setImageResource(data.getRes());
            }
        });
    }

    private List<BannerBean> loadBannerData() {
        List<BannerBean> bannerBeans = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BannerBean bannerBean = new BannerBean();
            bannerBean.setRes(R.drawable.banner_1);
            bannerBeans.add(bannerBean);
        }
        return bannerBeans;
    }
}