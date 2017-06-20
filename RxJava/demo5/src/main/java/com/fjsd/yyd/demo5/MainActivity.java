package com.fjsd.yyd.demo5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;
/*
* 1、组合的过程是分别从两个被观察者里各取出一个事件来进行组合,并且一个事件只能被使用一次,
*    组合的顺序是严格按照事件发送的顺利来进行的。
* 2、最终观察者收到的事件数量 是和被观察者中发送事件最少的那一个的事件数量相同
* */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建被观察者1
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "emit 1");
                e.onNext(1);
                Thread.sleep(1000);

                Log.d(TAG, "emit 2");
                e.onNext(2);
                Thread.sleep(1000);

                Log.d(TAG, "emit 3");
                e.onNext(3);
                Thread.sleep(1000);

                Log.d(TAG, "emit 4");
                e.onNext(4);
                Thread.sleep(1000);

                Log.d(TAG, "emit complete1");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());  //运行在IO线程

        //创建被观察者2
        Observable<String> observable2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.d(TAG, "emit A");
                e.onNext("A");
                Thread.sleep(1000);

                Log.d(TAG, "emit B");
                e.onNext("B");
                Thread.sleep(1000);

                Log.d(TAG, "emit C");
                e.onNext("C");
                Thread.sleep(1000);

                Log.d(TAG, "emit complete2");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());  //运行在另一个IO线程
        //zip对observable1和observable2发送的事件进行合并然后发给观察者，zip是一个静态方法
        Observable.zip(observable1, observable2, new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String value) {
                Log.d(TAG, "onNext: " + value);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        });
    }
}
