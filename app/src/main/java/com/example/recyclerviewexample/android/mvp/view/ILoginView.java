package com.example.recyclerviewexample.android.mvp.view;

public interface ILoginView extends IBaseMvpView {

    void loginSuccess();

    void loginFail();

    String getUser();

    String getPassword();

}
