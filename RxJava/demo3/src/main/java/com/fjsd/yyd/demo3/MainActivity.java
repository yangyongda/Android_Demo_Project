package com.fjsd.yyd.demo3;

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
* 1、subscribeOn() 指定的是被观察者发送事件的线程, observeOn() 指定的是观察者接收事件的线程.
* 2、多次指定被观察者的线程只有第一次指定的有效, 也就是说多次调用subscribeOn() 只有第一次的有效, 其余的会被忽略.
* 3、多次指定观察者的线程是可以的, 也就是说每调用一次observeOn() , 下游的线程就会切换一次.
* 4、在RxJava中, 已经内置了很多线程选项供我们选择, 例如有
        Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
        Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
        Schedulers.newThread() 代表一个常规的新线程
        AndroidSchedulers.mainThread() 代表Android的主线程
* */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //创建被观察者
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "Observable thread is : " + Thread.currentThread().getName());
                Log.d(TAG, "emit 1");
                e.onNext(1);
            }
        });

        //创建观察者(消费者)
        Consumer<Integer> consumer = new Consumer<Integer>() {
            /*
            * Consumer只接收被观察者的next事件，其他的事件不接收，这是它和Observer的区别
            * */
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "Observer thread is :" + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + integer);
            }
        };

        //连接观察者和被观察者
        //observable.subscribe(consumer);
        /*
        observable.subscribeOn(Schedulers.newThread())      //指定被观察者的线程
                .observeOn(AndroidSchedulers.mainThread()) //指定观察者的线程
                .subscribe(consumer);
         */
        observable.subscribeOn(Schedulers.newThread()) //被观察者只有第一次指定才有效
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) //观察者以最后一次为有效线程
                .observeOn(Schedulers.io())
                .subscribe(consumer);
    }
}
