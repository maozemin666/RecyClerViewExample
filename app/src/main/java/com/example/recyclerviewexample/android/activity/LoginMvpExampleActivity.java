package com.example.recyclerviewexample.android.activity;

import android.app.Dialog;
import android.os.Bundle;

import com.example.recyclerviewexample.android.mvp.presenter.LoginPresenter;
import com.example.recyclerviewexample.android.mvp.view.ILoginView;

public class LoginMvpExampleActivity extends BaseMvpActivity<ILoginView, LoginPresenter> implements ILoginView {

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData(Bundle saveInstanceState) {

    }

    @Override
    public Dialog getDialog() {
        return null;
    }

    @Override
    public void cancelLoadDialog() {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail() {

    }

    @Override
    public String getUser() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }
}
