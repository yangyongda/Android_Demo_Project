//
// Created by YongDa.Yang on 2017/5/14.
//

#include "com_tpv_yongdayang_ndkdemo_JniUtil.h"
JNIEXPORT jint JNICALL Java_com_tpv_yongdayang_ndkdemo_JniUtil_add
  (JNIEnv * env, jobject obj, jint a, jint b){

  return a + b;
 }