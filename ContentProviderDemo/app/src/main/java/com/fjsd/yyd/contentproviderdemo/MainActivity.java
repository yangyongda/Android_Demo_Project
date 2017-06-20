package com.fjsd.yyd.contentproviderdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
* 1、创建一个继承ContentProvider的类，并实现对应的方法
* 2、在AndroidManifest.xml中注册
*
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
