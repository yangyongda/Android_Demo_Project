package com.fjsd.yyd.servicedemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*
* 启动Service之startService方式
* 1、创建继承Service的类,实现相应的方法。
* 2、在AndroidMainfest.xml声明Service
* 3、startService启动Service
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button start;
    private Button stop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = (Button)findViewById(R.id.start);
        stop = (Button)findViewById(R.id.stop);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, MyService.class);
        switch (view.getId()){
            case R.id.start:
                startService(intent);
                break;
            case R.id.stop:
                stopService(intent);
                break;

        }
    }
}
