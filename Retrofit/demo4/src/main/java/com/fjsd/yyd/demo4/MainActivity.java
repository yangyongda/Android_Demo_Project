package com.fjsd.yyd.demo4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* Rxjava、Retrofit和Okhttp配合使用
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)       //设置连接超时
                .readTimeout(10000L,TimeUnit.MILLISECONDS)          //设置读取超时
                .writeTimeout(10000L,TimeUnit.MILLISECONDS)         //设置写入超时
                .cache(new Cache(getCacheDir(),10 * 1024 * 1024))   //设置缓存目录和10M缓存
                .addInterceptor(interceptor)    //添加日志拦截器（该方法也可以设置公共参数，头信息）
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)        //设置OkHttp
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create()) //  添加数据解析ConverterFactory
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())   //添加RxJava
                .build();

        ExpressService service = retrofit.create(ExpressService.class);

        service.searchRx("yuantong","500379523313")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PostQueryInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PostQueryInfo value) {
                        //成功结果返回
                        Log.e("APP",value.getNu()+ " " + value.getCondition());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
