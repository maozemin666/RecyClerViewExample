package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.Thread.CallableAchieve;
import com.example.recyclerviewexample.Thread.RunnableAchieve;
import com.example.recyclerviewexample.Thread.ThreadAchieve;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ThreadActivity extends AppCompatActivity {
    private static final String TAG = "maomaoThreadActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        init();
    }

    private void init() {
        threadTest();
    }

    private void threadTest() {
        ThreadAchieve threadAchieve = new ThreadAchieve();
        threadAchieve.start();

        RunnableAchieve runnableAchieve = new RunnableAchieve();
        Thread thread = new Thread(runnableAchieve);
        thread.start();

        CallableAchieve callableAchieve = new CallableAchieve();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> stringFuture = executorService.submit(callableAchieve);
        try {
            Log.d(TAG, "threadTest: stringFuture.get="+stringFuture.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
