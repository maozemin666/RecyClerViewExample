package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.customveiw.CanvasOperation;
import com.example.recyclerviewexample.customveiw.PathView;
import com.example.recyclerviewexample.customveiw.PieView;

import java.util.ArrayList;

public class CutomViewActivity extends AppCompatActivity {

    private PieView pieView;
    private FrameLayout main;
    private CanvasOperation canvasOperation;
    private PathView mPathView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cutom_view);
        initView();
        initData();
    }

    private void initView() {
        main = findViewById(R.id.main);
//        initPieView();
//        initCanvasOperation();
        initPath();
    }

    private void initPath() {
        mPathView = new PathView(this);
        FrameLayout.LayoutParams layoutParams
                = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        mPathView.setLayoutParams(layoutParams);
        main.addView(mPathView);
    }

    private void initCanvasOperation() {
        canvasOperation = new CanvasOperation(this);
        FrameLayout.LayoutParams layoutParams
                = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        canvasOperation.setLayoutParams(layoutParams);
        main.addView(canvasOperation);
    }

    private void initPieView() {
        pieView = new PieView(this);
        FrameLayout.LayoutParams layoutParams
                = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        pieView.setLayoutParams(layoutParams);
        main.addView(pieView);
    }

    private void initData() {
        ArrayList<PieView.PieData> datas = new ArrayList<>();
        PieView.PieData pieData = new PieView.PieData("sloop", 60);
        PieView.PieData pieData2 = new PieView.PieData("sloop", 30);
        PieView.PieData pieData3 = new PieView.PieData("sloop", 40);
        PieView.PieData pieData4 = new PieView.PieData("sloop", 20);
        PieView.PieData pieData5 = new PieView.PieData("sloop", 20);
        datas.add(pieData);
        datas.add(pieData2);
        datas.add(pieData3);
        datas.add(pieData4);
        datas.add(pieData5);
        if (pieView != null) {
            pieView.setData(datas);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
