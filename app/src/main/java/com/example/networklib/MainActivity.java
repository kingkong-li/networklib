package com.example.networklib;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.networklib.calllbacks.DefaultCallback;
import com.example.networklib.httpclient.HttpClient;
import com.example.networklib.httpclient.cache.CacheUtil;
import com.example.networklib.httpclient.dns.HttpDns;
import com.example.networklib.httpclient.interceptors.LogInterceptor;
import com.example.networklib.requests.GetRequest;
import com.example.networklib.requests.PostRequest;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author jingang.li
 */
public class MainActivity extends AppCompatActivity {
    public static final String BASE_URL = "http://capi.luckincoffee.com/";
    public static final String SHOP_LIST = "resource/s/ehr/shopList";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testOkHttp();

        testMyNetLibGet();
        testMyNetLibPost();

    }

    private void testMyNetLibPost() {
        new HttpClient.HttpClientBuilder().addInterceptor(new LogInterceptor())
                .dns(new HttpDns()).cache(CacheUtil.getDefaultCache(MainActivity.this))
                .build()
                .addRequest(new PostRequest.Builder("http://capi.luckincoffee.com/resource/m/sys/app/start2")
                        .addParameter("version", "130").build())
                .sendRequest(new DefaultCallback(){
                    @Override
                    public void onResponse(Call call, Response response) {
                        super.onResponse(call, response);
                        Log.e("JG","testMyNetLibPost response="+response.toString());
                    }
                });
    }


    private void testMyNetLibGet() {
        new HttpClient.HttpClientBuilder().addInterceptor(new LogInterceptor()).build()
                .addRequest(new GetRequest("http://www.baidu.com"))
                .sendRequest(new DefaultCallback(){
                    @Override
                    public void onResponse(Call call, Response response) {
                        super.onResponse(call, response);
                        Log.e("JG","testMyNetLibGet response="+response.toString());
                    }
                });

    }

    /**
     *  用okHttp进行网络请求
     */
    private void testOkHttp() {
        //构造OkHttpClient

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        // 增加拦截器
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();



                Log.v("TAG","chain.connectTimeoutMillis()="+chain.connectTimeoutMillis()) ;
                Log.v("TAG","chain.readTimeoutMillis()="+chain.readTimeoutMillis()) ;

                Log.v("TAG","chain.writeTimeoutMillis()="+chain.writeTimeoutMillis()) ;
                Log.v("TAG","chain.readTimeoutMillis()="+chain.readTimeoutMillis()) ;
                Response response = chain.proceed(request);
                return response;
            }
        });
        builder.addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response;
            }
        });


        //创建Request 对象--相当于写信
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .build();

        // 将Request封装为call
        //构造一个HttpClient 相当构造一个邮箱，选择一个邮箱并进行私有化配置
        OkHttpClient client=new OkHttpClient();
        //把信放进邮箱
        Call call = client.newCall(request);
        // 放置到请求队列、开始请求、邮箱开始发动信件，并等待回复。
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
               // 当请求被取消、连接中断、找不到服务器等问题会调用这个接口
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // 远程服务器成功返回调用
                final String res = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e("TAG"," "+res);
                    }
                });
            }
        });

    }
}
