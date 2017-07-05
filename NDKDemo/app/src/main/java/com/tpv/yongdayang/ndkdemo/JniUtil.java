package com.tpv.yongdayang.ndkdemo;

/**
 * Created by YongDa.Yang on 2017/5/14.
 */

public class JniUtil {

    static {
        System.loadLibrary("app");
    }
    public native int add(int a, int b );
}
