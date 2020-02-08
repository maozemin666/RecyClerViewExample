package com.example.recyclerviewexample.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.retrofit.GetRequest_Interface;
import com.example.recyclerviewexample.retrofit.GetYouDaoRequeset_Interface;
import com.example.recyclerviewexample.retrofit.PostRequest_Interface;
import com.example.recyclerviewexample.retrofit.Translation;
import com.example.recyclerviewexample.retrofit.Translation1;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitActivity extends AppCompatActivity {
    private static final String TAG = "RetrofitActivity";
    private static final String BAIDU_URL = "https://www.baidu.com/";
    private MediaType textType;
    private RequestBody file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        initView();
//        baseUse();
        exampleOne();
    }

    private void initView() {

    }

    private void baseUse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BAIDU_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetRequest_Interface service = retrofit.create(GetRequest_Interface.class);
        Call<ResponseBody> call =  service.testFormUrlEncoded("maomao",23);

        RequestBody name = RequestBody.create(textType, "maomao");
        RequestBody age = RequestBody.create(textType, "24");
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", "test.txt", file);

        Call<ResponseBody> call1 = service.testFileUpload(name, age, filePart);
        HashMap<String, Object> map = new HashMap<>();
        map.put("username","maomao");
        map.put("age",24);
        service.testFormUrlEncoded2(map);




    }

    private void exampleOne() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GetYouDaoRequeset_Interface getYouDao = retrofit.create(GetYouDaoRequeset_Interface.class);
        Call<Translation> translationCall = getYouDao.getCall();
        translationCall.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                response.body().show();
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });

        PostRequest_Interface postRequestInterface = retrofit.create(PostRequest_Interface.class);
        Call<Translation1> translation1Call = postRequestInterface.getCall("i love you");
        translation1Call.enqueue(new Callback<Translation1>() {
            @Override
            public void onResponse(Call<Translation1> call, Response<Translation1> response) {
                Log.d(TAG, "onResponse1: ");
            }

            @Override
            public void onFailure(Call<Translation1> call, Throwable t) {
                Log.d(TAG, "onFailure1: ");
            }
        });
    }


}
