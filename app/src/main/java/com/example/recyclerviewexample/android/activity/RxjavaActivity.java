package com.example.recyclerviewexample.android.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.thirdparty.rxjava2.RxjavaDemo;
import com.example.recyclerviewexample.thirdparty.rxjava2.RxjavaDemo2;
import com.example.recyclerviewexample.thirdparty.rxjava2.RxjavaDemo3;

import io.reactivex.disposables.CompositeDisposable;

public class RxjavaActivity extends BaseActivity {
    private static final String TAG = "maomaoRxjavaActivity";

    private TextView tv;

    private CompositeDisposable disposable = new CompositeDisposable();
    private RxjavaDemo3 rxjavaDemo3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_rxjava;
    }

    @Override
    public void initView() {
        tv = findViewById(R.id.tv);
    }

    @Override
    public void init() {
//        rxjavaTest();
//        rxjavaTest2();
        rxjavaTest3();
    }

    private void rxjavaTest() {
        RxjavaDemo rxjavaDemo = new RxjavaDemo(tv);
//        rxjavaDemo.testRxjava();
//        rxjavaDemo.simpleExmple();

//        rxjavaDemo.fromArrayTest();
//        rxjavaDemo.fromIterableTest();
//        rxjavaDemo.deferTest();
//        rxjavaDemo.timerTest();
//        rxjavaDemo.intervalTest();
//        rxjavaDemo.intervalRangeTest();
//        rxjavaDemo.rangeTest();
//        rxjavaDemo.doOnNext();

//        rxjavaDemo.concatTest();
//        rxjavaDemo.mergeTest();
//        rxjavaDemo.concatDelayErrorTest();


//        rxjavaDemo.doMap();
//        rxjavaDemo.doFlatMap();
//        rxjavaDemo.doConcatMap();
//        rxjavaDemo.doBuffer();

//        rxjavaDemo.doZip();
//        rxjavaDemo.doZip2();
//        rxjavaDemo.doCollect();
//        rxjavaDemo.startWithTest();
//        rxjavaDemo.doCount();
//        rxjavaDemo.combineLatestTest();
//        rxjavaDemo.doDisposableExample(disposable);
//        rxjavaDemo.doTake();
//        rxjavaDemo.doTimer();
//        rxjavaDemo.doInterval(disposable);

//        rxjavaDemo.doSingleExample();
//        rxjavaDemo.doCompletableObserver();
//        rxjavaDemo.doFlowableExample();
//        rxjavaDemo.doReduce();
//        rxjavaDemo.doBufferExample();
//        rxjavaDemo.doFilterExample();
//        rxjavaDemo.doSkipExample();
//        rxjavaDemo.doScanExample();
//        rxjavaDemo.doReplayExample();
//        rxjavaDemo.doConcatExample();
//        rxjavaDemo.doMergeExample();
//        rxjavaDemo.doDeferExample();
//        rxjavaDemo.doDistinctExample();
//        rxjavaDemo.doLastOperatorExample();

//        rxjavaDemo.doReplaySubjectExample();
//        rxjavaDemo.doPublishSubjectExample();
//        rxjavaDemo.doBehaviorSubjectExample();
//        rxjavaDemo.doAsyncSubjectExample();
//        rxjavaDemo.doThrottleFirstExample();
//        rxjavaDemo.doDebounceExample();
//        rxjavaDemo.doSwitchMap();
//        rxjavaDemo.doTakeWhileExample();
//        rxjavaDemo.doTakeUntilExample();

//        rxjavaDemo.onErrorReturnTest();
//        rxjavaDemo.onErrorResumeNextTest();
//        rxjavaDemo.onExceptionResumeNextTest();
//        rxjavaDemo.doRety();
//        rxjavaDemo.doRety2();
//        rxjavaDemo.retryWhenTest();
//        rxjavaDemo.repeatTest();
        rxjavaDemo.repeatWhenTest();

    }

    private void rxjavaTest2() {
        RxjavaDemo2 rxjavaDemo2 = new RxjavaDemo2(tv);
//        rxjavaDemo2.networkRequestPolling();
//        rxjavaDemo2.NetworkRequestNestedCallback();
//        rxjavaDemo2.getCachedData();
//        rxjavaDemo2.mergeDataSourcesAndShowAtTheSameTime();
//        rxjavaDemo2.jointJudgment(this);
//        rxjavaDemo2.networkRequestPollingForConditional();
        rxjavaDemo2.networkRequestErrorReconnection();
    }

    private void rxjavaTest3() {
        rxjavaDemo3 = new RxjavaDemo3(tv);
        rxjavaDemo3.threadTest();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        背景：在发送网络请求时 退出当前Activity
//        冲突：此时如果回到主线程更新 UI，App会崩溃
//        解决方案：当 Activity退出时，调用 Disposable.dispose()切断观察者和被观察者的连接，使得观察者无法收到事件 & 响应事件
        disposable.clear();
        if (rxjavaDemo3 != null) {
            rxjavaDemo3.onDestroy();
        }
    }
}
