package com.fjsd.yyd.demo3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
* Rxjava和Retrofit配合
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                //添加解析器
                .addConverterFactory(GsonConverterFactory.create())
                //添加RxJava
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        //创建服务器对象
        ExpressService service = retrofit.create(ExpressService.class);

        service.searchRx("yuantong","500379523313")
                //请求在IO线程
                .subscribeOn(Schedulers.io())
                //处理在主线程
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostQueryInfo>() {  //绑定观察者
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostQueryInfo value) {
                        //成功结果返回
                        Log.e("APP",value.getNu());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //错误回调
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        //请求结束回调
                    }
                });
    }
}
