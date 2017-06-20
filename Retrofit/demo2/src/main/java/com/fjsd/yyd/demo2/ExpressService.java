package com.fjsd.yyd.demo2;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public interface ExpressService {
    //http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号
    @POST("query")
    Call<PostQueryInfo> search(@Query("type") String type, @Query("postid") String postid);
}
