package com.fjsd.yyd.demo3;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

/*
* Targets 是没有任何别的回调，它在 Glide 做完所有的加载和处理之后返回结果
* */
public class MainActivity extends AppCompatActivity {
    String url = "http://image2.5399.com/2014/1021/20141021053850993.jpeg";
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView)findViewById(R.id.image3);
        SimpleTarget target = new SimpleTarget< Bitmap>(250,250) {  //指定图片大小
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                mImageView.setImageBitmap(resource);
            }
        };
        Glide.with( this )
                .load( url)
                .asBitmap()
                .into(target);  //使用target来处理加载结果，不让Glide自动设置
    }
}
