package com.example.recyclerviewexample.mvp.view;

import android.app.Dialog;

public interface IBaseMvpView {

    Dialog getDialog();

    void cancelLoadDialog();
}
