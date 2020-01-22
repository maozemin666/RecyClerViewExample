package com.example.recyclerviewexample.rxjava2;

import android.content.IntentFilter;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.recyclerviewexample.utils.AppConstant;
import com.example.recyclerviewexample.utils.UserUtils;

import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Flowable;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class RxjavaDemo {
    private static final String TAG = "maomaoRxjavaDemo";
    private TextView textView;

    public RxjavaDemo(TextView tv) {
        textView = tv;
    }

    public void testRxjava() {
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "thread: "+Thread.currentThread().getName());
                e.onNext("rxjava2");
                Log.d(TAG, "subscribe rxjava2: ");
                e.onNext("learn");
                Log.d(TAG, "subscribe: learn");
                e.onComplete();
                Log.d(TAG, "subscribe: onComplete");
                e.onNext("Android");
                Log.d(TAG, "subscribe: Android");
            }
        });
        Observer<String> observer = new Observer<String>() {
            private Disposable disposable;
            private int i;

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: ");
                disposable = d;
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: value="+value);
                i++;
                if (i == 1) {
                    Log.d(TAG, "disposable: ");
                    disposable.dispose();
                    Log.d(TAG, "isDisposed: "+disposable.isDisposed());
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        };
        //建立连接，只有建立上游才开始发送事件
        observable.subscribe(observer);
    }

    public void simpleExmple() {
        getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Observable<String> getObservable() {
        return Observable.just("Cricket","Football");
    }

    private Observer<String> getObserver() {
        return new Observer<String>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                Log.d(TAG, "onSubscribe: "+disposable.isDisposed());
            }

            @Override
            public void onNext(String s) {
                Log.d(TAG, "onNext: " + s);
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, " onError : " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, " onComplete");
            }
        };
    }

    public void doMap() {
        getObservable2()
                .subscribeOn(Schedulers.io())
                .map(new Function<List<UserUtils.ApiUser>, List<UserUtils.User>>() {
                    @Override
                    public List<UserUtils.User> apply(List<UserUtils.ApiUser> apiUsers) throws Exception {
                        return UserUtils.convertApiUserListToUserList(apiUsers);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver2());
    }


    private Observable<List<UserUtils.ApiUser>> getObservable2() {
        return Observable.create(new ObservableOnSubscribe<List<UserUtils.ApiUser>>() {
            @Override
            public void subscribe(ObservableEmitter<List<UserUtils.ApiUser>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(UserUtils.getApiUserList());
                    e.onComplete();
                }
            }
        });
    }

    private Observer<List<UserUtils.User>> getObserver2() {
        return new Observer<List<UserUtils.User>>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                Log.d(TAG, "onSubscribe: "+disposable.isDisposed());
            }

            @Override
            public void onNext(List<UserUtils.User> s) {
                for (UserUtils.User user : s) {
                    Log.d(TAG, "onNext: " + user.firstname);
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.d(TAG, " onError : " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d(TAG, " onComplete");
            }
        };
    }

    public void doZip() {
        Observable.zip(getCricketFansObservable(), getFootballFansObservable(),
                new BiFunction<List<UserUtils.User>, List<UserUtils.User>, List<UserUtils.User>>() {
                    @Override
                    public List<UserUtils.User> apply(List<UserUtils.User> cricketfans, List<UserUtils.User> footballfans) throws Exception {
                        return UserUtils.filterUserWhoLovesBoth(cricketfans,footballfans);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver3());
    }

    private Observable<List<UserUtils.User>> getFootballFansObservable() {
        return Observable.create(new ObservableOnSubscribe<List<UserUtils.User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<UserUtils.User>> e) throws Exception {
                if (!e.isDisposed()) {
                    Log.d(TAG, "subscribe: "+Thread.currentThread().getName());
                    e.onNext(UserUtils.getUserListWhoLovesFootball());
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private Observable<List<UserUtils.User>> getCricketFansObservable() {
        return Observable.create(new ObservableOnSubscribe<List<UserUtils.User>>() {
            @Override
            public void subscribe(ObservableEmitter<List<UserUtils.User>> e) throws Exception {
                if (!e.isDisposed()) {
                    Log.d(TAG, "subscribe: "+Thread.currentThread().getName());
                    e.onNext(UserUtils.getUserListWhoLovesCricket());
                    e.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private Observer<List<UserUtils.User>> getObserver3() {
        return new Observer<List<UserUtils.User>>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                Log.d(TAG, "onSubscribe: "+disposable.isDisposed());
            }

            @Override
            public void onNext(List<UserUtils.User> s) {
                textView.append("onNext");
                textView.append("\n");
                for (UserUtils.User user : s) {
                    textView.append(" firstname : " + user.firstname);
                    textView.append("\n");
                    Log.d(TAG, "onNext: " + user.firstname);
                }
                Log.d(TAG, " onNext size: " + s.size());
            }

            @Override
            public void onError(Throwable throwable) {
                textView.append(" onError : " + throwable.getMessage());
                textView.append("\n");
                Log.d(TAG, " onError : " + throwable.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete : ");
                textView.append("\n");
                Log.d(TAG, " onComplete");
            }
        };
    }


    public void doDisposableExample(CompositeDisposable disposable) {
        disposable.add(simpleObserable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        textView.append("onNext");
                        textView.append("\n");
                        textView.append(" onNext : value : " + s);
                        textView.append("\n");
                        Log.d(TAG, " onNext value : " + s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        textView.append(" onError : " + throwable.getMessage());
                        textView.append("\n");
                        Log.d(TAG, " onError : " + throwable.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete : ");
                        textView.append("\n");
                        Log.d(TAG, " onComplete");
                    }
                }));
    }

    private Observable<String> simpleObserable() {
        return Observable.defer(new Callable<ObservableSource<? extends String>>() {
            @Override
            public ObservableSource<? extends String> call() throws Exception {
                SystemClock.sleep(1000);
                return Observable.just("one", "two", "three", "four", "five");
            }
        });
    }

    public void doTake() {
        Observable.just(2, 3, 5, 7, 6)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(3)
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Integer value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doTimer() {
        Observable.timer(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Long value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext : value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doInterval(CompositeDisposable disposable) {
        disposable.add(Observable.interval(2,1,TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.d(TAG, "accept: "+Thread.currentThread().getName());
                    }
                })
                .subscribeWith(new DisposableObserver<Long>() {
                    @Override
                    public void onNext(Long value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext : value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                }));
    }

    public void doSingleExample() {
        Single.just("Anim")
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onSuccess(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }
                });
    }

    public void doCompletableObserver() {
        Completable.timer(1000,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }
                });
    }

    public void doFlowableExample() {
        //reduce 聚合操作
        Flowable<Integer> flowable = Flowable.just(1, 2, 4, 3);
        flowable.reduce(10, new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer + integer2;
            }
        })
        .subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onSuccess(Integer value) {
                textView.append(" onSuccess : value : " + value);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onSuccess : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onError : " + e.getMessage());
            }
        });
    }

    public void doReduce() {
        Flowable.just(1,2,3,10)
                .reduce(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer - integer2;  //累减
                    }
                })
                .subscribe(new MaybeObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onSuccess(Integer value) {
                        textView.append(" onSuccess : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onSuccess : value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    /**
     * buffer(int count, int skip) count是buffer的最大值 skip是步长
     */
    public void doBufferExample() {
        // 3 means,  it takes max of three from its start index and create list
        // 1 means, it jumps one step every time
        // so the it gives the following list
        // 1 - one, two, three
        // 2 - two, three, four
        // 3 - three, four, five
        // 4 - four, five
        // 5 - five
        Observable.just(1,2,3,4,5)
                .buffer(3,1)
                .subscribe(new Observer<List<Integer>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(List<Integer> stringList) {
                        textView.append(" onNext size : " + stringList.size());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext : size :" + stringList.size());
                        for (Integer value : stringList) {
                            textView.append(" value : " + value);
                            textView.append(AppConstant.LINE_SEPARATOR);
                            Log.d(TAG, " : value :" + value);
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doFilterExample() {
        Observable.just(1,2,3,4,5)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer % 2 == 0;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Integer value) {
                        textView.append(" onNext : ");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        textView.append(" value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext ");
                        Log.d(TAG, " value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doSkipExample() {
        Observable.just(1,2,3,4,5)
                .skip(3)
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Integer value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doScanExample() {
//        我们的被观察者中有5个数，分别是1,2,3,4,5
//        scan的过程：
//        第一个参数1不够两个，直接发射结果1；
//        之后：
//        t1 = 1；t2 = 2
//        计算：t1 + t2 = 3，发射结果3；
//        t1 = 3；t2 = 3；
//        计算： t1 + t2 = 6，发射结果6；
//        t1 = 6；t2 = 4；
//        计算 ：t1 + t2 = 10，发射结果10；
//        t1 = 10；t2 = 5；
//        计算 ：t1 + t2 = 15，发射结果15；
//        结束
        Observable.just(1,2,3,4,5)
                .scan(new BiFunction<Integer, Integer, Integer>() {
                    @Override
                    public Integer apply(Integer integer, Integer integer2) throws Exception {
                        return integer + integer2;
                    }
                })
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Integer value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doReplayExample() {
        PublishSubject<Integer> source = PublishSubject.create();
        ConnectableObservable<Integer> connectableObservable = source.replay(3); // bufferSize = 3 to retain 3 values to replay
        connectableObservable.connect();

        connectableObservable.subscribe(getFirstObserver());

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

        connectableObservable.subscribe(getSecondObserver());
    }

    private Observer<Integer> getSecondObserver() {
        return new Observer<Integer>() {

            @Override
            public void onSubscribe(Disposable d) {
                textView.append(" Second onSubscribe : isDisposed :" + d.isDisposed());
                Log.d(TAG, " Second onSubscribe : " + d.isDisposed());
                textView.append(AppConstant.LINE_SEPARATOR);
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" Second onNext : value : " + value);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " Second onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" Second onError : " + e.getMessage());
                Log.d(TAG, " Second onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" Second onComplete");
                Log.d(TAG, " Second onComplete");
            }
        };
    }

    private Observer<Integer> getFirstObserver() {
        return new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                textView.append(" First onSubscribe : isDisposed :" + d.isDisposed());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " First onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(Integer value) {
                textView.append(" First onNext : value : " + value);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " First onNext value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" First onError : " + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " First onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" First onComplete");
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " First onComplete");
            }
        };
    }

    public void doConcatExample() {
        final String[] aStrings = {"A1","A2","A3","A4"};
        final String[] bStrings = {"B1", "B2", "B3"};
        final Observable<String> aobservable = Observable.fromArray(aStrings);
        final Observable<String> bobservable = Observable.fromArray(bStrings);
        Observable.concat(aobservable,bobservable)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext : value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    /**
         * 合并多个Observables的发射物， Merge 可能会让合并的Observables发射的数据交错，这也就是和concat的较大区别(挨个发送)
     */
    public void doMergeExample() {
        final String[] aStrings = null;
        final String[] bStrings = {"B1", "B2", "B3"};
        final Observable<String> aobservable = Observable.fromArray(aStrings);
        final Observable<String> bobservable = Observable.fromArray(bStrings);
        Observable.merge(aobservable,bobservable)
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext : value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    String brand;
    public void doDeferExample() {
        Observable<String> observable = Observable.defer(new Callable<ObservableSource<String>>() {
            @Override
            public ObservableSource<String> call() throws Exception {
                return Observable.just(brand);
            }
        });

//        Observable<String> observable2 = Observable.just(brand);
        brand="BMW"; // Even if we are setting the brand after creating Observable

        // we will get the brand as BMW.
        // If we had not used defer, we would have got null as the brand.

        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, " onSubscribe : " + d.isDisposed());
            }

            @Override
            public void onNext(String value) {
                textView.append(" onNext : value : " + value);
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onNext : value : " + value);
            }

            @Override
            public void onError(Throwable e) {
                textView.append(" onError : " + e.getMessage());
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onError : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                textView.append(" onComplete");
                textView.append(AppConstant.LINE_SEPARATOR);
                Log.d(TAG, " onComplete");
            }
        });
    }

    /**
     *防止重复项
     */
    public void doDistinctExample() {
        Observable.just("maomo","ze","maomao","mao","mao","ze")
                .distinct()
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doLastOperatorExample() {
        Observable.just("A1", "A2", "A3", "A4", "A5", "A6")
                .last("A1")  // the default item ("A1") to emit if the source ObservableSource is empty
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onSuccess(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }


                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }
                });
    }

    /** ReplaySubject emits to any observer all of the items that were emitted
     * by the source Observable, regardless of when the observer subscribes.
     */
    public void doReplaySubjectExample() {
        ReplaySubject<Integer> replaySubject = ReplaySubject.create();
        replaySubject.subscribe(getFirstObserver());

        replaySubject.onNext(1);
        replaySubject.onNext(2);
        replaySubject.onNext(3);
        replaySubject.onNext(4);
        replaySubject.onComplete();

        replaySubject.subscribe(getSecondObserver());
    }

    /**
     * 它发射源Observable被订阅之后的所有值。
     * 如果一个学生上课迟到啦，他只想听到他进入教室后的内容。此时Publish是最适合这个场景。
     */
    public void doPublishSubjectExample() {
        PublishSubject<Integer> publishSubject = PublishSubject.create();
        publishSubject.subscribe(getFirstObserver());// it will get 1, 2, 3, 4 and onComplete

        publishSubject.onNext(1);
        publishSubject.onNext(2);
        publishSubject.onNext(3);

        /*
         * it will emit 4 and onComplete for second observer also.
         */
        publishSubject.subscribe(getSecondObserver());

        publishSubject.onNext(4);
        publishSubject.onComplete();
    }


    /* When an observer subscribes to a BehaviorSubject, it begins by emitting the item most
     * recently emitted by the source Observable (or a seed/default value if none has yet been
     * emitted) and then continues to emit any other items emitted later by the source Observable(s).
     * It is different from Async Subject as async emits the last value (and only the last value)
     * but the Behavior Subject emits the last and the subsequent values also.
     */
    public void doBehaviorSubjectExample() {
        BehaviorSubject<Integer> source = BehaviorSubject.create();
        source.subscribe(getFirstObserver());// it will get 1, 2, 3, 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        /*
         * it will emit 3(last emitted), 4 and onComplete for second observer also.
         */
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();
    }

    /* An AsyncSubject emits the last value (and only the last value) emitted by the source
     * Observable, and only after that source Observable completes. (If the source Observable
     * does not emit any values, the AsyncSubject also completes without emitting any values.)
     */
    public void doAsyncSubjectExample() {
//        它只发射源Observable的最后一个值，之后Observable便结束啦。
//        如果一个学生在任何一个时刻进入教室，而且他只想他听到教授讲解的最后一个内容，之后变下课啦。此时我们会使用Async.
        AsyncSubject<Integer> source = AsyncSubject.create();
        source.subscribe(getFirstObserver());// it will emit only 4 and onComplete

        source.onNext(1);
        source.onNext(2);
        source.onNext(3);

        /*
         * it will emit 4 and onComplete for second observer also.
         */
        source.subscribe(getSecondObserver());

        source.onNext(4);
        source.onComplete();
    }


    public void doThrottleFirstExample() {
//        一定时间内取第一次发送的事件。
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Thread.sleep(0);
                emitter.onNext(1); // deliver
                emitter.onNext(2); // skip
                Thread.sleep(505);
                emitter.onNext(3); // deliver
                Thread.sleep(99);
                emitter.onNext(4); // skip
                Thread.sleep(100);
                emitter.onNext(5); // skip
                emitter.onNext(6); // skip
                Thread.sleep(305);
                emitter.onNext(7); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        })
        .throttleFirst(500,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Integer value) {
                        textView.append(" onNext : ");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        textView.append(" value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext ");
                        Log.d(TAG, " value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doDebounceExample() {
//        关键部分看被观察者这块；
//        第一个事件1发送出来以后过了400毫秒后发送出了第二个事件，此时不事件1不满足时间的条件被遗弃，然后重新计时；
//        2发出后休眠了505毫秒，超过了500毫秒，所以2被发射了出来，被观察者收到；
//        3发出来后又过了100毫秒4发出来，所以3被遗弃，从4重新计时，后又过了605毫秒下一个事件才发出，所以4被发射了出来；
//        同理，5之后的0.5秒内也没有再发出别的事件，所以最终5也被发射了出来。
//
//        类似一个弹簧，如果一个事件相当于挤压它一下的话，它回到初始状态需要一段时间，那如果一直有事件不断的挤压它，那它一直回不到初始状态，就一个事件也弹不出来。一旦有一段时间里面没有人挤压它，他就把最后一个弹出来了。周而复始
        Observable.create(new ObservableOnSubscribe<Integer>() {

            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                // send events with simulated time wait
                emitter.onNext(1); // skip
                Thread.sleep(400);
                emitter.onNext(2); // deliver
                Thread.sleep(505);
                emitter.onNext(3); // skip
                Thread.sleep(100);
                emitter.onNext(4); // deliver
                Thread.sleep(605);
                emitter.onNext(5); // deliver
                Thread.sleep(510);
                emitter.onComplete();
            }
        }).debounce(500,TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(Integer value) {
                        textView.append(" onNext : ");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        textView.append(" value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext ");
                        Log.d(TAG, " value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });

    }

    public void doSwitchMap() {
        Observable.just(1, 2, 3, 4, 5)
                .switchMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        int delay = new Random().nextInt(2);
                        Log.d(TAG, "apply: "+integer);

                        return Observable.just(integer + "x")
                                .delay(delay,TimeUnit.SECONDS,Schedulers.io());
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        textView.append(" onError : " + e.getMessage());
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onError : " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });
    }

    public void doTakeWhileExample() {
        Observable.just("Alpha", "Beta", "Cupcake", "Doughnut", "Eclair", "Froyo", "GingerBread",
                "Honeycomb", "Ice cream sandwich")
                .zipWith(Observable.interval(0, 1, TimeUnit.SECONDS), new BiFunction<String, Long, String>() {

                    @Override
                    public String apply(String s, Long aLong) throws Exception {
                        return s+aLong;
                    }
                })
                .takeWhile(new Predicate<String>() {
                    @Override
                    public boolean test(String s) throws Exception {
                        return !s.toLowerCase().contains("honey");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });

    }

    public void doTakeUntilExample() {
        Observable<Long> observable = Observable.timer(5,TimeUnit.SECONDS);
        observable.subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        String print = " Timer completed";
                        textView.append(print);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, print);
                    }
                });

        Observable.just("Alpha", "Beta", "Cupcake", "Doughnut", "Eclair", "Froyo", "GingerBread",
                "Honeycomb", "Ice cream sandwich")
                .zipWith(Observable.interval(0, 5, TimeUnit.SECONDS), new BiFunction<String, Long, String>() {

                    @Override
                    public String apply(String s, Long aLong) throws Exception {
                        return s+aLong;
                    }
                })
                .takeUntil(observable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, " onSubscribe : " + d.isDisposed());
                    }

                    @Override
                    public void onNext(String value) {
                        textView.append(" onNext : value : " + value);
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onNext value : " + value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        textView.append(" onComplete");
                        textView.append(AppConstant.LINE_SEPARATOR);
                        Log.d(TAG, " onComplete");
                    }
                });

    }
}
