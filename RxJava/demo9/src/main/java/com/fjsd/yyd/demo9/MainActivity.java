package com.fjsd.yyd.demo9;

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
* BackpressureStrategy.BUFFER
* BackpressureStrategy.DROP
* BackpressureStrategy.LATEST
* 分别对应
* onBackpressureBuffer()
  onBackpressureDrop()
  onBackpressureLatest()
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
/****************************************************************************************
 *      通过BackpressureStrategy.BUFFER来加大缓冲区，该缓冲区大小不确定，根据实际情况分配
 *
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 1000; i++) {   //发送1000次事件
                    Log.d(TAG, "emit " + i);
                    emitter.onNext(i);
                }
            }
            //BackpressureStrategy.BUFFER会比默认的缓冲区大，但是并不能根本上解决OOM
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {

                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG, "onSubscribe");
                        mSubscription = s;
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
 **********************************************************************************************/

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
                request(128);  //请求一次，被观察者就从缓冲区中取出128个发给观察者
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
                for (int i = 0;i<10000 ; i++) {  //无限发事件
                    emitter.onNext(i);
                }
            }
            //BackpressureStrategy.DROP:直接把存不下的事件丢弃
            //BackpressureStrategy.LATEST:只保留最新的事件(除前128事件是默认保留的，处理完前128个后才能只保留最新的事件)
        }, BackpressureStrategy.LATEST).subscribeOn(Schedulers.io())
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
