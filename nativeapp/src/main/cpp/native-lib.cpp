//
// Created by jianhao on 2020/3/15.
//

#include "com_example_nativeapp_NativeMsg.h"
#include <jni.h>
#include <string>
extern "C"
JNIEXPORT jstring JNICALL Java_com_example_nativeapp_NativeMsg_getMsg
        (JNIEnv* env, jobject thiz) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());

}