LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := helloJNI
LOCAL_SRC_FILES := HelloJNI.cpp

include $(BUILD_SHARED_LIBRARY)