package com.fjsd.yyd.glide;

import com.fjsd.yyd.glide.bean.GetInfo;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public interface ImageAPI {
    @GET("10/{index}")
    Observable<GetInfo> getInfo(@Path("index") String index);
}
