package com.fjsd.yyd.photoviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.chrisbanes.photoview.PhotoView;
/*
*PhotoView特性：
* 1、支持单点/多点触摸，即时缩放图片；(双击缩放图片)
* 2、支持平滑滚动；
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        photoView.setImageResource(R.drawable.image);

    }
}
