package com.example.networklib.httpclient.dns;

import android.util.Log;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;

/**
 * @author jingang.li
 */
public class HttpDns implements Dns {
    private static final String TAG = HttpDns.class.getSimpleName();

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        Log.v(TAG, "lookup:" + hostname);
        //只需要在lookup方法中调用HttpDns的SDK去获取IP
        // 如果获取到了就返回一个List<InetAddress>的值
        // 如果购买了阿里的HttpDns服务就可以用
        //默认又返回系统的DNS解析，这就叫DNS降级
        return SYSTEM.lookup(hostname);
    }
}
