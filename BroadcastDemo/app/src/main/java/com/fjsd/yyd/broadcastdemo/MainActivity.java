package com.fjsd.yyd.broadcastdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
* 静态注册：
* 1、创建一个接收类继承BroadcastReceiver类，并覆写onReceive()方法
* 2、在AndroidMainfest.xml注册接收类
* 3、发送广播
* */
public class MainActivity extends AppCompatActivity {
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //com.fjsd.yyd.MY_BROADCAST:要接收广播的BroadcastReceiver类的action
                Intent intent = new Intent("com.fjsd.yyd.MY_BROADCAST");
                sendBroadcast(intent);
            }
        });

    }
}
