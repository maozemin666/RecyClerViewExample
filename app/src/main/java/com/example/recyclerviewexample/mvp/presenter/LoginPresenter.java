package com.example.recyclerviewexample.mvp.presenter;

import com.example.recyclerviewexample.mvp.model.ILoginModel;
import com.example.recyclerviewexample.mvp.model.impl.LoginModeImpl;
import com.example.recyclerviewexample.mvp.view.ILoginView;

public class LoginPresenter extends BaseMvpPresenter<ILoginView> {

    private ILoginModel iLoginModel;

    public LoginPresenter() {
        iLoginModel = new LoginModeImpl();
    }

    public void handleLogin() {
        iLoginModel.handleLogin();
    }
}
