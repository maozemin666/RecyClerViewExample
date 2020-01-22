package com.example.recyclerviewexample.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.serialization.HistoryBook;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "maomao2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        init();
    }

    private void init() {
        serializableTest();
    }

    private void serializableTest() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        final HistoryBook historyBook = new HistoryBook();
        bundle.putParcelable("historyBook",historyBook);
        intent.putExtras(bundle);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.d(TAG, "onNewIntent: ");
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart: ");
        super.onRestart();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: ");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

}
