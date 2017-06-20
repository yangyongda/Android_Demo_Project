package com.fjsd.yyd.demo4;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/12/19 0019.
 */
public interface ExpressService {
    @POST("query")
    Observable<PostQueryInfo> searchRx(@Query("type") String type, @Query("postid") String postid);
}
