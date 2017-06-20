package com.fjsd.yyd.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
/*
* onComplete和onError必须唯一并且互斥, 即不能发多个onComplete,
* 也不能发多个onError, 也不能先发一个onComplete, 然后再发一个onError,
* 反之亦然。
* 简单说这两个方法最终只能有一个被调用
*
* 互斥理解：回调onComplete方法说明已经完成任务了，所以也就不会再出现异常了，所以不会回调onError
*           回调onError方法说明程序已经出现异常，已经中断了，也就不能再回调onComplete方法
* */
public class MainActivity extends AppCompatActivity {
    public static final String TAG = "observer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);  //发出next事件
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onComplete(); //发出complete事件
            }
        });
        //创建观察者
        Observer<Integer> observer = new Observer<Integer>() {
            /*
            * 观察者和被观察者连接时回调
            * */
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "subscribe");
            }
            /*
            * 处理next事件时回调
            * */
            @Override
            public void onNext(Integer value) {
                Log.d(TAG, "" + value);
            }
            /*
            * 事件处理过程中出异常时，onError() 会被触发
            * */
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "error");
            }
            /*
            * 处理complete事件时回调
            * */
            @Override
            public void onComplete() {
                Log.d(TAG, "complete");
            }
        };

        //将被观察者和观察者进行连接
        observable.subscribe(observer);
    }
}
