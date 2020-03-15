package com.example.nativeapp;

public class NativeMsg {

    static {
        System.loadLibrary("nativeMsg");
    }

    public native String getMsg();

}
