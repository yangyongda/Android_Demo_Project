package com.fjsd.yyd.aidl;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*被其他应用访问：
* 1、定义AIDL文件，在接口中定义方法
* 2、创建继承Service的类,并实现onBind方法，该方法返回接口的Stub内部类（比如IMyAidlInterface.Stub）
* 3、在Service类中定义Stub成员(private IMyAidlInterface.Stub mBinder),并实现之前在接口中定义的方法
* 4、在AndroidMainfest.xml中声明Service，并添加<action>,该action是其他进程访问该进程的Intent中要设置的
*    还要在<service>中添加android:process=":remote"
*
*    注意：aidl文件生成的java文件在build/generated/source/aidl/debug中（要编译工程才能生成）
* */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
