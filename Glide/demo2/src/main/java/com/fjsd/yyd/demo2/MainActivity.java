package com.fjsd.yyd.demo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView mImage = (ImageView)findViewById(R.id.image2);
/***********************************显示gif图片*******************************
        String gifUrl = "http://i.kinja-img.com/gawker-media/image/upload/s--B7tUiM5l--/gf2r69yorbdesguga10i.gif";
        Glide.with(this)
                .load(gifUrl)  //显示gif图片和普通的图片一样
                .asBitmap()   //转换成bitmap图片
                .into(mImage);
 **************************************************************************/
        /*
    diskCacheStrategy方法参数值:
          DiskCacheStrategy.NONE 什么都不缓存
          DiskCacheStrategy.SOURCE 仅仅只缓存原来的全分辨率的图像
          DiskCacheStrategy.RESULT 仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
          DiskCacheStrategy.ALL 缓存所有版本的图像（默认行为）
        * */
        String internetUrl = "http://i.imgur.com/DvpvklR.png";
        Glide.with(this)
                .load(internetUrl)
                .skipMemoryCache(true)     //跳过内存缓存
                .diskCacheStrategy( DiskCacheStrategy.NONE ) //不进行磁盘缓存
                .thumbnail(0.1f)   //显示原始图像的10%的大小
                .into(mImage);

    }
}
