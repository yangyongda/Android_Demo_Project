package com.fjsd.yyd.customview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
* 自定义控件步骤：
* 1、自定义View的属性，在res/values/ 下建立一个attrs.xml，在里面定义我们的属性和声明我们的整个样式
* 2、定义继承View的类，在构造器中获取属性(默认的布局文件调用的是两个参数的构造方法)
* 3、重写onDraw()和onMeasure()
* 4、在布局文件中声明该自定义控件
*
* 注意：要声明命名控件xmlns:custom="http://schemas.android.com/apk/res-auto"
*       ，以后就可以使用custom:自定义属性=" "来定义控件,比如：custom:titleText="TestContent"
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
