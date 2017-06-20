package com.fjsd.yyd.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/*
* 1、添加网络访问权限
* 2、创建服务器接口interface
* 3、创建Retrofit对象并设置基本URL和数据解析器
* 4、创建服务器对象
* 5、创建响应体
* 6、发送异步或同步请求
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()     //获取Retrofit对象
                .baseUrl("https://api.github.com/") //设置基本URL，也就是http请求的url前缀,可以把项目中重复的前缀用这个来设置
                .addConverterFactory(GsonConverterFactory.create()) //添加Gson数据解析ConverterFactory
                .build();
        //创建服务器对象
        GitHubService service = retrofit.create(GitHubService.class);
        //创建一个响应体
        Call<ResponseBody> repos = service.listRepos("octocat");
        //get异步请求
        repos.enqueue(new Callback<ResponseBody>() {
            //数据处理
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e("APP",response.body().source().toString());
            }
            //请求失败处理
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
