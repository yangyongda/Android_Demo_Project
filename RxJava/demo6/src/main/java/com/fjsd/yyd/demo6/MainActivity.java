package com.fjsd.yyd.demo6;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
/*
* 1、如果一个被观察者发送事件特别快, 而另一个被观察者发送事件特别慢，zip为每个被观察者
*    开辟了一块缓冲区，但是缓冲区的大小有限，当存储的事件越来越多时会出现内存溢出。
* 2、被观察者和观察者工作在同一个线程中时, 这时候是一个同步的订阅关系, 也就是说被观察者每发送
*    一个事件必须等到观察者接收处理完了以后才能接着发送下一个事件.
* 3、当被观察者和观察者工作在不同的线程中时, 这时候是一个异步的订阅关系, 这个时候
*    被观察者发送数据不需要等待观察者接收，可以一直发送。
* 4、同步和异步的区别仅仅在于是否有缓冲区
* */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*****************************************************************
        //被观察者和观察者在同一线程，内存很平稳
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {   //无限循环发事件
                    emitter.onNext(i);
                }
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Thread.sleep(2000);
                Log.d(TAG, "" + integer);
            }
        });
 *******************************************************************/
        //被观察者和观察者不在同一个线程，内存急剧上升
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; ; i++) {    //无限循环发事件
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())   //被观察者在IO线程
                .observeOn(AndroidSchedulers.mainThread())  //观察者在UI线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Thread.sleep(2000);
                        Log.d(TAG, "" + integer);
                    }
                });
    }
}
