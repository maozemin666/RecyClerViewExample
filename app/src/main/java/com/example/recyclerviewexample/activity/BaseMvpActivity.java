package com.example.recyclerviewexample.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.recyclerviewexample.mvp.IActivity;
import com.example.recyclerviewexample.mvp.presenter.BaseMvpPresenter;
import com.example.recyclerviewexample.mvp.view.IBaseMvpView;

public abstract class BaseMvpActivity<V extends IBaseMvpView, P extends BaseMvpPresenter<V>>
        extends AppCompatActivity implements IActivity, IBaseMvpView {

    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachMvpView((V) this);
        initView();
        initData(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachMvpView();
        }
    }
}
