package com.fjsd.yyd.bindservice;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*启动Service之bindService方式
* 1、创建继承Service的类,实现相应的方法,主要是onBind()；并创建继承Binder的内部类，在内部类中创建所需的方法
* 2、在AndroidMainfest.xml声明Service
* 3、在Activity中声明ServiceConnection匿名内部类对象,在onServiceConnected()方法中获取Binder对象
* 4、调用bindService()
*
* 注意：Binder对象是Activity和Service通信的枢纽
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button bind;
    private Button unbind;
    private MyService.MyBinder myBinder;
    /*
    * Activity和Service的连接通道
    * */
    private ServiceConnection connection = new ServiceConnection() {
        /*
        * Activity与Service解除关联时调用
        * */
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
        /*
        * Activity与Service建立关联时调用
        * */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService.MyBinder) service;
            myBinder.startDownload();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bind = (Button)findViewById(R.id.bind);
        unbind = (Button)findViewById(R.id.unbind);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bind:
                Intent bindIntent = new Intent(this, MyService.class);
                //BIND_AUTO_CREATE表示在Activity和Service建立关联后自动创建Service
                bindService(bindIntent, connection, BIND_AUTO_CREATE);  //绑定service
                break;
            case R.id.unbind:
                unbindService(connection);   //解绑service
                break;
        }
    }
}
