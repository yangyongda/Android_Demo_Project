package com.fjsd.yyd.aidltest;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fjsd.yyd.aidl.IMyAidlInterface;
/*
*访问其他应用
* 1、拷贝aidl文件到该工程，aidl文件对应的包名要和原先的一样
* 2、编译生成对应的接口类
* 3、定义ServiceConnection， 使用Stub的asInterface方法将IBinder转成接口类
* 4、调用bindService启动Service
* 5、调用接口类中的方法实现两个进程间的通信
* */
public class MainActivity extends AppCompatActivity {
    private IMyAidlInterface myAidlInterface;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                int result = myAidlInterface.plus(50, 50);
                String upperStr = myAidlInterface.toUpperCase("comes from ClientTest");
                Toast.makeText(MainActivity.this,  "result is " + result+"\n"+"upperStr is " + upperStr, Toast.LENGTH_SHORT).show();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((Button)findViewById(R.id.bind_service)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.fjsd.yyd.MyService");
                intent.setPackage("com.fjsd.yyd.aidl");
                bindService(intent,mConnection,BIND_AUTO_CREATE);
            }
        });

    }
}
