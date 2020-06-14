package com.example.recyclerviewexample.thirdparty.rxjava2;

import android.util.Log;
import android.widget.TextView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * https://www.jianshu.com/p/c935d0860186
 */
public class RxjavaDemo3 {
    private static final String TAG = "maomaoRxjavaDemo3";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    private TextView textView;

    public RxjavaDemo3(TextView tv) {
        textView = tv;
    }

    /**
     * rxjava 线程的类型
     * <p>
     * Schedulers.computation()：用于计算任务，默认线程数等于处理器的数量。
     * Schedulers.from(Executor executor)：使用Executor作为调度器，关于Executor框架可以参考这篇文章：多线程知识梳理(5) - 线程池四部曲之 Executor 框架。
     * Schedulers.io( )：用于IO密集型任务，例如访问网络、数据库操作等，也是我们最常使用的。
     * Schedulers.newThread( )：为每一个任务创建一个新的线程。
     * Schedulers.trampoline( )：当其它排队的任务完成后，在当前线程排队开始执行。
     * Schedulers.single()：所有任务共用一个后台线程。
     * <p>
     * 以上是在io.reactivex.schedulers包中，提供的Schedulers，而如果我们导入了下面的依赖，那么在io.reactivex.android.schedulers下，还有额外的两个Schedulers可选：
     * <p>
     * AndroidSchedulers.mainThread()：运行在应用程序的主线程。
     * AndroidSchedulers.from(Looper looper)：运行在该looper对应的线程当中。
     */
    public void threadTest() {
        Observable observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                for (int i = 0; i < 100; i++) {
                    if (i % 20 == 0) {
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException ex) {
                            if (!e.isDisposed()) {
                               e.onError(ex);
                            }
                        }
                        e.onNext(1);
                    }
                }
                e.onComplete();
            }
        });

        DisposableObserver disposableObserver = new DisposableObserver<Integer>() {
            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "onNext=" + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError=" + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };

        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(disposableObserver);
        compositeDisposable.add(disposableObserver);
    }

    public void onDestroy() {
        compositeDisposable.clear();
    }

}
