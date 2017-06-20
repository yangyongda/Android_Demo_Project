package com.fjsd.yyd.systembroadcast;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
*系统广播：
* 在注册BroadcastReceiver时使用系统广播的action
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
