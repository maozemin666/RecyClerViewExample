package com.example.recyclerviewexample.thirdparty.rxjava2;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recyclerviewexample.R;
import com.example.recyclerviewexample.android.activity.RxjavaActivity;
import com.example.recyclerviewexample.thirdparty.retrofit.GetYouDaoRequeset_Interface;
import com.example.recyclerviewexample.thirdparty.retrofit.Translation;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxjavaDemo2 {

    private static final String TAG = "maomaoRxjavaDemo2";

    private TextView textView;

    public RxjavaDemo2(TextView textView) {
        this.textView = textView;
    }

    public void networkRequestPolling() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext((integer) -> {
                    Log.d(TAG, "第 " + integer + " 次轮询");

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://fy.iciba.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //支持rxjava2
                            .build();

                    GetYouDaoRequeset_Interface service = retrofit.create(GetYouDaoRequeset_Interface.class);
                    Observable<Translation> observable = service.getCall2();
                    observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<Translation>() {
                                @Override
                                public void onSubscribe(Disposable d) {

                                }

                                @Override
                                public void onNext(Translation value) {
                                    Log.d(TAG, "onNext");
                                    value.show();
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.d(TAG, "请求失败=" + e.getMessage());
                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应=" + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });
    }

    int i = 0;

    public void networkRequestPollingForConditional() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //支持rxjava2
                .build();

        GetYouDaoRequeset_Interface service = retrofit.create(GetYouDaoRequeset_Interface.class);
        Observable<Translation> observable = service.getCall2();
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) throws Exception {
                        Log.d(TAG, "apply: i=" + i);
                        if (i > 3) {
                            return Observable.error(new Throwable("轮询结束"));
                        }

                        return Observable.just(1).delay(2, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Translation result) {
                        // e.接收服务器返回的数据
                        result.show();
                        i++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    // 设置变量
    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    public void networkRequestErrorReconnection() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //支持rxjava2
                .build();

        GetYouDaoRequeset_Interface service = retrofit.create(GetYouDaoRequeset_Interface.class);
        Observable<Translation> observable = service.getCall2();
        // 注：主要异常才会回调retryWhen（）进行重试
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        // 输出异常信息
                        Log.d(TAG,  "发生异常 = "+ throwable.toString());

                        if (throwable instanceof IOException) {
                            if (currentRetryCount < maxConnectCount) {
                                currentRetryCount++;
                                waitRetryTime = 1000 + 1000 * currentRetryCount;
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.SECONDS);
                            } else {
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " +currentRetryCount  + "，即 不再重试"));
                            }
                        }
                        return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Translation result) {
                        // e.接收服务器返回的数据
                        result.show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    public void NetworkRequestNestedCallback() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetYouDaoRequeset_Interface service = retrofit.create(GetYouDaoRequeset_Interface.class);
        Observable<Translation> register = service.getReguster();
        Observable<Translation> login = service.getLogin();

        register.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        Log.d(TAG, "第1次网络请求成功");
                        translation.show();
                    }
                })

                .observeOn(Schedulers.io())

                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
                    @Override
                    public ObservableSource<Translation> apply(Translation result) throws Exception {
                        return login;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {
                        Log.d(TAG, "第2次网络请求成功");
                        translation.show();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "登录失败");
                    }
                });
    }

    public void getCachedData() {
        String memeryCache = null;
        String diskCache = "磁盘缓存";
        Observable observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (memeryCache != null) {
                    e.onNext(memeryCache);
                } else {
                    e.onComplete();
                }
            }
        });

        Observable observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if (diskCache != null) {
                    e.onNext(diskCache);
                } else {
                    e.onComplete();
                }
            }
        });

        Observable observable3 = Observable.just("从网络上获取数据");

        /*
         * 通过concat（） 和 firstElement（）操作符实现缓存功能
         **/

        // 1. 通过concat（）合并memory、disk、network 3个被观察者的事件（即检查内存缓存、磁盘缓存 & 发送网络请求）
        //    并将它们按顺序串联成队列
        Observable.concat(observable1, observable2, observable3)
                // 2. 通过firstElement()，从串联队列中取出并发送第1个有效事件（Next事件），即依次判断检查memory、disk、network
                .firstElement()
                // 即本例的逻辑为：
                // a. firstElement()取出第1个事件 = memory，即先判断内存缓存中有无数据缓存；由于memoryCache = null，即内存缓存中无数据，所以发送结束事件（视为无效事件）
                // b. firstElement()继续取出第2个事件 = disk，即判断磁盘缓存中有无数据缓存：由于diskCache ≠ null，即磁盘缓存中有数据，所以发送Next事件（有效事件）
                // c. 即firstElement()已发出第1个有效事件（disk事件），所以停止判断。
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG, "最终获取的数据来源 =  " + s);
                    }
                });
    }

    public void mergeDataSourcesAndShowAtTheSameTime() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        GetYouDaoRequeset_Interface service = retrofit.create(GetYouDaoRequeset_Interface.class);
        Observable<Translation> getData1 = service.getWorldCall().subscribeOn(Schedulers.io());
        Observable<Translation> getData2 = service.getChinaCall().subscribeOn(Schedulers.io());

        Observable.zip(getData1, getData2, new BiFunction<Translation, Translation, String>() {
            @Override
            public String apply(Translation translation, Translation translation2) throws Exception {
                return translation.show1() + "&" + translation2.show1();
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "accept: s=" + s);
                textView.setText(s);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG, "accept: " + throwable.getMessage());
            }
        });
    }

    public void jointJudgment(RxjavaActivity activity) {
        View view = LayoutInflater.from(activity).inflate(R.layout.activity_rxjava_joint_judgment, null);
        activity.setContentView(view);

        EditText name, age, job;
        Button list;
        name = (EditText) view.findViewById(R.id.name);
        age = (EditText) view.findViewById(R.id.age);
        job = (EditText) view.findViewById(R.id.job);
        list = (Button) view.findViewById(R.id.list);

        Observable<CharSequence> nameObservable = RxTextView.textChanges(name).skip(1);
        Observable<CharSequence> ageObservable = RxTextView.textChanges(age).skip(1);
        Observable<CharSequence> jobObservable = RxTextView.textChanges(job).skip(1);

        Observable.combineLatest(nameObservable, ageObservable, jobObservable
                , new Function3<CharSequence, CharSequence, CharSequence, Boolean>() {
                    @Override
                    public Boolean apply(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) throws Exception {
                        boolean a = !TextUtils.isEmpty(charSequence);
                        boolean b = !TextUtils.isEmpty(charSequence2);
                        boolean c = !TextUtils.isEmpty(charSequence3);
                        Log.d(TAG, "apply: ");
                        return a && b && c;
                    }
                })
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d(TAG, "accept: ");
                        list.setEnabled(aBoolean);
                    }
                });

    }


}
