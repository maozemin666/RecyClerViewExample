package com.example.recyclerviewexample.android.mvp.view;

import android.app.Dialog;

public interface IBaseMvpView {

    Dialog getDialog();

    void cancelLoadDialog();
}
