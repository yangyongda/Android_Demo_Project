package com.tpv.yongdayang.hellojni;

/**
 * Created by YongDa.Yang on 2017/5/26.
 */

public class HelloJni {
    static {
        System.loadLibrary("helloJNI");
    }
    //Native方法
    public static native String getFormCString();

}
