//
// Created by YongDa.Yang on 2017/5/26.
//

#include "com_tpv_yongdayang_hellojni_HelloJni.h"

JNIEXPORT jstring JNICALL Java_com_tpv_yongdayang_hellojni_HelloJni_getFormCString
  (JNIEnv * env, jclass clazz)
  {
        //通过env里面的转换方法，将字符串转成UTF格式的jstring
        return env->NewStringUTF("I am from C String!");
  }