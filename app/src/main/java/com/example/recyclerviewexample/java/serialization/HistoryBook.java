package com.example.recyclerviewexample.java.serialization;

import android.os.Parcel;
import android.util.Log;

public class HistoryBook extends Book {
    private static final String TAG = "HistoryBook";

    public HistoryBook() {
        super();
    }

    protected HistoryBook(Parcel in) {
        super(in);
    }

    @Override
    void Job() {
        Log.d(TAG, "Job: "+"historyBook");
    }
}
