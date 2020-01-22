package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.rxjava2.RxjavaDemo;

import io.reactivex.disposables.CompositeDisposable;

public class RxjavaActivity extends AppCompatActivity {

    private TextView tv;

    private CompositeDisposable disposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        init();
    }

    private void init() {
        tv = findViewById(R.id.tv);
        rxjavaTest();
    }

    private void rxjavaTest() {
        RxjavaDemo rxjavaDemo = new RxjavaDemo(tv);
//        rxjavaDemo.testRxjava();
//        rxjavaDemo.simpleExmple();
//        rxjavaDemo.doMap();
//        rxjavaDemo.doZip();
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
        rxjavaDemo.doTakeUntilExample();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposable.clear();
    }
}
