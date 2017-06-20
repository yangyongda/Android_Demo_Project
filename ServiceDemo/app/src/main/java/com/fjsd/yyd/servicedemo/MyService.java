package com.fjsd.yyd.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 *
 * onCreate() --> onStartCommand() --> onDestory()
 */

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    /*
    * 首次创建服务时，系统将调用此方法
    * */
    @Override
    public void onCreate() {
        //初始化数据
        Log.v("TAG","onCreate");
    }
    /*
    * 当另一个组件（如 Activity）通过调用 startService() 请求启动服务时，系统将调用此方法
    * */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //处理业务逻辑
        Log.v("TAG","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }
    /*
    * 当服务不再使用且将被销毁时，系统将调用此方法
    * */
    @Override
    public void onDestroy() {
        //回收资源
        Log.v("TAG","onDestroy");
    }
}
