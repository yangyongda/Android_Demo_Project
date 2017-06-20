package com.fjsd.yyd.bindservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * onCreate()-->onBind()-->onDestroy()
 * 使用bindService()进行绑定时onStartCommand()不会被调用
 */

public class MyService extends Service {

    private MyBinder mBinder = new MyBinder();

    /*
    * 使用bindService()绑定时会调用这个方法，该方法返回的IBinder对象会传递给ServiceConnection对象
    * */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
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

    /*
    * 定义一个内部类MyBinder，
    * */
    class MyBinder extends Binder {

        public void startDownload() {
            Log.v("TAG", "startDownload() executed");
            // 执行具体的下载任务
        }

    }
}
