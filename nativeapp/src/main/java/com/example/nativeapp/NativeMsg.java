package com.example.nativeapp;

public class NativeMsg {

    static {
        System.loadLibrary("native-lib");
    }

    public native String getMsg();

}
