package com.example.recyclerviewexample.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class OrderUtil2_0 {

    public static Map<String, String> buildOrderParamMap(String app_id, boolean rsa2) {
        Map<String, String> keyValues = new HashMap<>();
        keyValues.put("app_id", app_id);
        keyValues.put("biz_content", "{\"timeout_express\":\"30m\",\"product_code\":\"QUICK_MSECURITY_PAY\",\"total_amount\":\"0.01\",\"subject\":\"1\",\"body\":\"我是测试数据\",\"out_trade_no\":\"" + getOutTradeNo() +  "\"}");
        keyValues.put("charset", "utf-8");
        keyValues.put("method", "alipay.trade.app.pay");
        keyValues.put("sign_type", rsa2 ? "RSA2" : "RSA");
        keyValues.put("timestamp", "2016-07-29 16:55:53");
        keyValues.put("version", "1.0");
        return keyValues;
    }

    public static String buildOrderParam(Map<String, String> map) {
        List<String> keys = new ArrayList<>(map.keySet());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();
    }

    private static String buildKeyValue(String key, String value, boolean isEnCode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        if (isEnCode) {
            try {
                sb.append(URLEncoder.encode(value,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                sb.append(value);
            }
        } else {
            sb.append(value);
        }
        return sb.toString();
    }

    private static String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random random = new Random();
        key = key + random.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    public static String getSign(Map<String, String> map, String rsaKey, boolean rsa2) {
        List<String> keys  = new ArrayList<String>(map.keySet());
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);
            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey, rsa2);

        String encodeKey = "";
        try {
            encodeKey = URLEncoder.encode(oriSign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return "sign=" + encodeKey;
    }
}
