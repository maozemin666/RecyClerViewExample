package com.example.recyclerviewexample.thirdparty.retrofit;


import com.example.recyclerviewexample.bean.User;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GetRequest_Interface {
//    1.
    @GET("openapi.do?keyfrom=Yanzhikai&key=2032414398&type=data&doctype=json&version=1.1&q=car")
    Call<User> getCall();
    //@GET注解作用：采用get方式请求
    //getCall = 接收网络请求参数的方法
    //其中返回类型为Call<*>, *代表接收数据的类

//    2.@HTTP 扩展@GET、@POST、@PUT、@DELETE、@HEAD
    @HTTP(method = "GET", path = "blog/{id}", hasBody = false)
    Call<ResponseBody> getCall(@Path("id") int id);
    //{id}表示一个变量

    /**
     *表明是一个表单格式的请求（Content-Type:application/x-www-form-urlencoded）
     * <code>Field("username")</code> 表示将后面的 <code>String name</code> 中name的取值作为 username 的值
     */
    @POST("/form")
    @FormUrlEncoded
    Call<ResponseBody> testFormUrlEncoded(@Field("username") String name, @Field("age") int age);

    /**
     * {@link Part} 后面支持三种类型，{@link RequestBody}、{@link okhttp3.MultipartBody.Part} 、任意类型
     * 除 {@link okhttp3.MultipartBody.Part} 以外，其它类型都必须带上表单字段({@link okhttp3.MultipartBody.Part} 中已经包含了表单字段的信息)，
     */
    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload(@Part("username") RequestBody name, @Part("age") RequestBody age, @Part MultipartBody.Part file);

    @POST("/form")
    @Multipart
    Call<ResponseBody> testFileUpload2(@PartMap Map<String, RequestBody>  args);

//    3.
    @GET("user")
    Call<User> getUser(@Header("Authorization") String authorization);

    @Headers("Authorization: authorization")
    @GET("user")
    Call<User> getUser();
// 以上的效果是一致的。
// 区别在于使用场景和使用方式
// 1. 使用场景：@Header用于添加不固定的请求头，@Headers用于添加固定的请求头
// 2. 使用方式：@Header作用于方法的参数；@Headers作用于方法

//    4.@Body
//    FormBody.Builder builder = new FormBody.Builder();
//    builder.add("key","value");

//    5. @Field
//    6.@FieldMap
    @POST("/form")
    Call<ResponseBody> testFormUrlEncoded2(@FieldMap Map<String, Object> map);

//    7.用于 @GET 方法的查询参数（Query = Url 中 ‘?’ 后面的 key-value）
    @GET("/")
    Call<String> cate(@Query("cate") String cate);

//    8.@Path 用于URL中的缺省值
    @GET("users/{user}/repos")
    Call<ResponseBody> getBlog(@Path("user") String user);

//    9.@Url 直接传入一个请求的 URL变量 用于URL设置
    @GET
    Call<ResponseBody> testUrlQuery(@Url String url, @Query("showAll") boolean showAll);
    // 当有URL注解时，@GET传入的URL就可以省略
    // 当GET、POST...HTTP等方法中没有设置Url时，则必须使用 {@link Url}提供


}
