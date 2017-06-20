package com.fjsd.yyd.dynamic;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
/*
* 动态注册：
* 1、创建一个接收类继承BroadcastReceiver类，并覆写onReceive()方法
* 2、在代码中使用registerReceiver()注册接收类
* 3、发送广播
* */
public class MainActivity extends AppCompatActivity {
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //动态注册
        IntentFilter intentFilter = new IntentFilter(); //过滤器
        intentFilter.addAction("com.fjsd.yyd.Dynamic");
        MyBroadcastReceiver receiver = new MyBroadcastReceiver();
        registerReceiver(receiver, intentFilter);

        send = (Button)findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.fjsd.yyd.Dynamic");
                sendBroadcast(intent);
            }
        });
    }
}
