package com.fjsd.yyd.demo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
* 异步Post请求
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ExpressService service = retrofit.create(ExpressService.class);
        Call<PostQueryInfo> repos = service.search("yuantong","500379523313");
        repos.enqueue(new Callback<PostQueryInfo>() {
            @Override
            public void onResponse(Call<PostQueryInfo> call, Response<PostQueryInfo> response) {
                Log.d("TAG",response.body().getNu() + " " + response.body().getState() );
            }

            @Override
            public void onFailure(Call<PostQueryInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
