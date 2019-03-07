package com.example.networklib.httpclient.interceptors;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jingang.li
 */
public class LogInterceptor implements Interceptor {
    private static final String TAG=LogInterceptor.class.getSimpleName();
    @Override
    public Response intercept(Chain chain) throws IOException {

       // 把请求request拦截下来
        Request request = chain.request();
       //可以打印请求内容
        Log.v(TAG,"request method="+request.method()+",request url="+request.url());
        //继续向下一个传递处理，并拦截到处理结果response
        Response response = chain.proceed(request);
        // 可以打印返回内容
        Log.v(TAG,"response="+response.toString());
        return response;
    }
}
