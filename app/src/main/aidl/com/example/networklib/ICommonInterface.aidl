
package com.example.networklib;

// 1. 必须显式 import 另一个 AIDL 接口
import com.example.networklib.ICommonCallback;

interface ICommonInterface {

    void set(String data);

    String get();

    void registerCallback(in ICommonCallback callback);
}