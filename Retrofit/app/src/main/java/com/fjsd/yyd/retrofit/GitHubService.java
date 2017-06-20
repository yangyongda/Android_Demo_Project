package com.fjsd.yyd.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/12/16 0016.
 */
public interface GitHubService {
    //ResponseBody 这个是okhttp里面的对象，可以直接返回整个字符串，也可以获取流的形式
    @GET("users/{user}/repos")   //GET请求
    Call<ResponseBody> listRepos(@Path("user") String user);
}

