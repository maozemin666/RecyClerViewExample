package com.example.recyclerviewexample.mvp.presenter;

import com.example.recyclerviewexample.mvp.view.IBaseMvpView;

import java.lang.ref.WeakReference;

public class BaseMvpPresenter<V extends IBaseMvpView> {
    protected V mView;

    private WeakReference<V> weakReference;

    public void attachMvpView(V view) {
        if (view != null) {
            weakReference = new WeakReference<V>(view);
            mView = weakReference.get();
        }
    }

    public void detachMvpView() {
        weakReference.clear();
        weakReference = null;
        mView = null;
    }

    protected V getView() {
        if (mView == null) {
            throw new IllegalStateException("view not attach");
        } else {
            return mView;
        }
    }
}
