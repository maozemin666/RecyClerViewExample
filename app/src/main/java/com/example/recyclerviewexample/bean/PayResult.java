package com.example.recyclerviewexample.bean;

import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.util.Map;

public class PayResult {

    private String resultStatus;
    private String result;
    private String memo;

    public PayResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return;
        }

        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                resultStatus = rawResult.get(key);
            } else if (TextUtils.equals(key, "result")) {
                result = rawResult.get(key);
            }  else if (TextUtils.equals(key, "memo")) {
                memo = rawResult.get(key);
            }
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}";
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public String getResult() {
        return result;
    }

    public String getMemo() {
        return memo;
    }
}
