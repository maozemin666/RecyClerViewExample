package com.example.recyclerviewexample.retrofit;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetYouDaoRequeset_Interface {

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello%20world")
    Call<Translation> getCall();


    @GET("ajax.php?a=fy&f=auto&t=auto&w=hello world")
    Observable<Translation> getCall2();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20register")
    Observable<Translation> getReguster();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20login")
    Observable<Translation> getLogin();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<Translation> getWorldCall();

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20china")
    Observable<Translation> getChinaCall();
}
