package com.example.recyclerviewexample.android.activity;

import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import com.example.recyclerviewexample.R;

public class LoaderActivity extends AppCompatActivity {
    private static final String TAG = "maomaoLoaderActivity";

    public static final int ALL_MODE = 1;
    private MomentsCallbacks mMomentsCallbacks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loader);
        initLoader();
    }

    private void initLoader() {
        mMomentsCallbacks = new MomentsCallbacks();
        getLoaderManager().initLoader(ALL_MODE, null, mMomentsCallbacks);
    }



    public static class MomentsCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Log.d(TAG, "onCreateLoader: ");
            return null;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            Log.d(TAG, "onLoadFinished: ");

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {
            Log.d(TAG, "onLoaderReset: ");
        }
    }
}
