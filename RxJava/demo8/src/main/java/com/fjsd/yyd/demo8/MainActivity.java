package com.fjsd.yyd.demo8;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/*
* 1、被观察者使用Flowable，创建Flowable的时候增加了一个参数，该参数用来选择当出现BackPressure
*   的时候的解决办法。
* 2、在被观察者的onSubscribe方法中传给我们的不再是Disposable了, 而是Subscription,
*   之前我们说调用Disposable.dispose()方法可以切断连接, 同样的调用Subscription.cancel()
*   也可以切断连接。区别在于Subscription增加了一个void request(long n)方法。
* 3、request方法请求几个被观察者就发送几个给观察者（被观察者的事件会存放在缓冲区）
* 4、Flowable里默认有一个大小为128的缓冲区
* */
public class MainActivity extends AppCompatActivity {

    private static final String TAG ="TAG" ;
    private static Subscription mSubscription;
    private Button mStart;
    private Button mRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/******************************************同一线程*********************************************
        //被观察者
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.d(TAG, "emit 1");
                e.onNext(1);
                Log.d(TAG, "emit 2");
                e.onNext(2);
                Log.d(TAG, "emit 3");
                e.onNext(3);
                Log.d(TAG, "emit complete");
                e.onComplete();
            }
        }, BackpressureStrategy.ERROR); //第二个参数表示出现BackPressure的时候直接抛出一个异常
        //观察者
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                //Subscription.cancel()可以切断连接
                Log.d(TAG, "onSubscribe");
                //设置请求事件数
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(Throwable t) {
                Log.w(TAG, "onError: ", t);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete");
            }
        };
        //绑定观察者和被观察者
        flowable.subscribe(subscriber);
***********************************************************************************/

        mStart = (Button)findViewById(R.id.start);
        mRequest = (Button)findViewById(R.id.request);
        mStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start();
            }
        });

        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request(1);  //请求一次，被观察者就从缓冲区中取出一个发给观察者
            }
        });
    }

    public static void request(long n) {
        mSubscription.request(n); //在外部调用request请求上游
    }

    public static void start() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG, "emit 1");
                emitter.onNext(1);
                Log.d(TAG, "emit 2");
                emitter.onNext(2);
                Log.d(TAG, "emit 3");
                emitter.onNext(3);
                Log.d(TAG, "emit complete");
                emitter.onComplete();
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;  //把Subscription保存起来
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG, "onNext: " + integer);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.w(TAG, "onError: ", t);
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }
}

