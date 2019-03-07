package com.example.networklib.httpclient.cache;

import android.content.Context;

import com.example.networklib.MainActivity;

import java.io.File;

import okhttp3.Cache;

/**
 * 可以做成单例模式、
 * 这里可以用的上枚举
 * @author jingang.li
 */
public class CacheUtil {
    public static Cache getDefaultCache(Context context) {

        return new Cache(new File(context.getCacheDir(), "net_cache") ,1028L);
    }
}
