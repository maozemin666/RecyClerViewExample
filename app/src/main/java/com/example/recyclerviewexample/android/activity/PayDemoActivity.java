package com.example.recyclerviewexample.android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;

import com.alipay.sdk.app.PayTask;
import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.bean.PayResult;
import com.example.recyclerviewexample.utils.OrderUtil2_0;

import java.util.Map;

public class PayDemoActivity extends AppCompatActivity {

    public static final String APPID = "";
    public static final String PSA_PRIVATE= "";
    public static final String PSA2_PRIVATE= "";
    public static final int SDK_PAY_FLAG = 1;


    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    if (msg.obj instanceof Map) {
                        @SuppressWarnings("unchecked")
                        PayResult payResult = new PayResult((Map<String, String>) msg.obj);

                    }
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void payV2(View view) {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(PSA2_PRIVATE) && TextUtils.isEmpty(PSA_PRIVATE))) {
            showAlert(this, "错误: 需要配置 PayDemoActivity 中的 APPID 和 RSA_PRIVATE");
            return;
        }
        boolean rsa2 = PSA2_PRIVATE.length() > 0;
        Map<String, String> params = OrderUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? PSA2_PRIVATE : PSA_PRIVATE;
        String sign = OrderUtil2_0.getSign(params, privateKey, rsa2);

        final String orderInfo = orderParam + "&" + sign;

        Runnable payRunnable = () -> {
            PayTask aliPay = new PayTask(PayDemoActivity.this);
            Map<String, String> result  = aliPay.payV2(orderInfo, true);

            Message message = Message.obtain(mHandler, SDK_PAY_FLAG, result);
            message.sendToTarget();
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener listener) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("confirm", null)
                .setOnDismissListener(listener)
                .show();
    }
}
