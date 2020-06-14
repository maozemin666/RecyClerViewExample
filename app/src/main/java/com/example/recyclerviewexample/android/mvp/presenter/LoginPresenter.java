package com.example.recyclerviewexample.android.mvp.presenter;

import com.example.recyclerviewexample.android.mvp.model.ILoginModel;
import com.example.recyclerviewexample.android.mvp.model.impl.LoginModeImpl;
import com.example.recyclerviewexample.android.mvp.view.ILoginView;

public class LoginPresenter extends BaseMvpPresenter<ILoginView> {

    private ILoginModel iLoginModel;

    public LoginPresenter() {
        iLoginModel = new LoginModeImpl();
    }

    public void handleLogin() {
        iLoginModel.handleLogin();
    }
}
