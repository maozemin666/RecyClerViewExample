package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.recyclerviewexample.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkhttpActivity extends AppCompatActivity {
    private static final String TAG = "maomaoOkhttpActivity";
    private final String url = "https://www.wanandroid.com/navi/json";
    private final String url2 = "https://www.baidu.com";
    private final String url_login = "https://www.wanandroid.com/user/login";


    private OkHttpClient okHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttp);

        okHttpClient = new OkHttpClient();

        getAsynHttp();

        new Thread(() -> {
            getAsynHttp2();
        }).start();

        post();

        doraemonKit();
    }

    /**
     * 异步get请求
     */
    private void getAsynHttp() {

        final Request.Builder builder = new Request.Builder()
                .url(url2)
                .get();

        Request request = builder.build();

        final Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: ");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (null != response.cacheResponse()) {
                    String cacheResponse = response.cacheResponse().toString();
                    Log.d(TAG, "onResponse: cacheResponse="+cacheResponse);
                } else {
                    String body = response.body().toString();
                    Log.d(TAG, "onResponse: body = "+body);
                    String networkResponse = response.networkResponse().toString();
                    Log.d(TAG, "onResponse: networkResponse = "+networkResponse);
                }
                runOnUiThread(() -> {
                    Toast.makeText(OkhttpActivity.this, "request success", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    /**
     * 同步get请求
     */
    public void getAsynHttp2() {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        Call call = okHttpClient.newCall(request);
        try {
            Response response = call.execute();
            Log.d(TAG, "sycn result = "+response.body().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void post() {
        //使用formbody传递键值对
        RequestBody requestBody= new FormBody.Builder()
                .add("username","qinzishuai")
                .add("password","111111")
                .build();

        Request request = new Request.Builder()
                .url(url_login)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "onFailure: "+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: post="+response.body().toString());
            }
        });
    }

    public void post1() {
        //使用requestbody传递json或file对象
        MediaType jsonType = MediaType.parse("application/json;charset=utf-8");
        String jsonStr = "{\"username\":\"lisi\",\"nickname\":\"xiaoming\"}";

        RequestBody requestBody = RequestBody.create(jsonType,jsonStr);
        Request request = new Request.Builder()
                .url(url2)
                .post(requestBody)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                }
            }
        });


        MediaType fileType = MediaType.parse("File/*");
        String path = "com/example/recyclerviewexample/okhttp/okhttp.txt";
        File file = new File(path);
        RequestBody requestBody1 = RequestBody.create(fileType,file);
        Request request1 = new Request.Builder()
                .url(url2)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request1).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });


//        使用MultipartBody同时传递键值对参数和File对象
        MultipartBody multipartBody = new MultipartBody.Builder()
                .addFormDataPart("username","zemin")
                .addFormDataPart("sex","man")
                .addFormDataPart("file",file.getName()
                        ,RequestBody.create(fileType,file))
                .build();
        Request request2 = new Request.Builder()
                .url(url2)
                .post(multipartBody)
                .build();
        okHttpClient.newCall(request2).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


    public void setHeader() {
        new Request.Builder()
                .url(url2)
                .header("User-Agnt","okhttp")
                .addHeader("token","myToken")
                .build();
    }

    private void doraemonKit() {
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .addNetworkInterceptor()
//                .addInterceptor()
//                .build();
    }
}
