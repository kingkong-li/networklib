package com.example.networklib;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.networklib.calllbacks.DefaultCallback;
import com.example.networklib.httpclient.HttpClient;
import com.example.networklib.httpclient.cache.CacheUtil;
import com.example.networklib.httpclient.dns.HttpDns;
import com.example.networklib.httpclient.interceptors.LogInterceptor;
import com.example.networklib.requests.GetRequest;
import com.example.networklib.requests.PostRequest;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;

/**
 * @author jingang.li
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG =MainActivity.class.getSimpleName() ;
    private static final int SUCCESS =0 ;
    private static final int ERROR =1 ;
    private ImageButton mImageButton;
    private MyHandler mUIHandler;
    private TextView mTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        notifyLifeCycle("onCreate", "start");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageButton=findViewById(R.id.image_button);
        mTextView =findViewById(R.id.text_view);
        mUIHandler =new MyHandler(this);

        testOkHttp();

        testMyNetLibGet();
        testMyNetLibPost();
        testMyNetLibGetPhoto();

        testFlowResponse();
        DataController.INSTANCE.getWelComeData().observe( this, welcomeData -> {
            Log.d(TAG, "welcomeData="+welcomeData);
            mTextView.setText(welcomeData);
        });

        notifyLifeCycle("onCreate", "end");

    }

    @Override
    protected void onResume() {
        notifyLifeCycle("onResume", "start");
        super.onResume();
        notifyLifeCycle("onResume", "end");
    }

    @Override
    protected void onRestart() {
        notifyLifeCycle("onRestart", "start");
        super.onRestart();
        notifyLifeCycle("onRestart", "end");
    }

    @Override
    protected void onPause() {
        notifyLifeCycle("onPause", "start");
        super.onPause();
        notifyLifeCycle("onPause", "end");
    }

    @Override
    protected void onStop() {
        notifyLifeCycle("onStop", "start");
        super.onStop();
        notifyLifeCycle("onStop", "end");
    }

    @Override
    protected void onDestroy() {
        notifyLifeCycle("onDestroy", "start");
        super.onDestroy();
        notifyLifeCycle("onDestroy", "end");
    }

    private void testFlowResponse() {
        OkHttpClient client = new OkHttpClient.Builder().build();
        // 创建请求
        Request request = new Request.Builder()
                .url("http://47.121.191.103:8080/ping")
                .build();

        // 使用 EventSource 来监听服务器事件
        EventSource.Factory factory = EventSources.createFactory(client);
        factory.newEventSource(request, new EventSourceListener() {
            @Override
            public void onOpen(EventSource eventSource, okhttp3.Response response) {
                Log.d(TAG, "SSE connection opened");
            }

            @Override
            public void onEvent(EventSource eventSource, String id, String type, String data) {
                // 处理服务器推送的数据
                Log.d(TAG, "New SSE event: type=" + type + ", data=" + data);
            }

            @Override
            public void onClosed(EventSource eventSource) {
                Log.d(TAG, "SSE connection closed");
            }

            @Override
            public void onFailure(EventSource eventSource, Throwable t, okhttp3.Response response) {
                Log.e(TAG, "SSE connection failed", t);
            }
        });

    }

    private class MyHandler extends Handler{
        private final WeakReference<Activity> mWeakReferenceWrapper;
        MyHandler(Activity activity){
            mWeakReferenceWrapper = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity=mWeakReferenceWrapper.get();
            super.handleMessage(msg);
            if(activity!=null){
                //执行业务逻辑
                switch (msg.what){
                    case SUCCESS:
                        mImageButton.setImageBitmap((Bitmap) msg.obj);
                        break;
                    case ERROR:
                        break;
                    default:
                }
            }
        }
    }




    private void testMyNetLibGetPhoto() {
        new HttpClient.HttpClientBuilder().build()
                .addRequest(new GetRequest("http://f1.haiqq.com/allimg/1813156232/376663358.jpg"))
                .sendRequest(new DefaultCallback(){
                    @Override
                    public void onResponse(Call call, Response response) {
                        super.onResponse(call, response);
                        Log.v(TAG,"testMyNetLibGetPhoto onResponse");

                        try {
                            if(response.body()!=null) {
                                byte[] picture = response.body().bytes();
                                //使用BitmapFactory工厂，把字节数组转化为bitmap
                                Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                                //通过ImageView，设置图片

                                //使用Handler发送消息
                                Message msg = Message.obtain();
                                msg.what = SUCCESS;
                                msg.obj = bitmap;
                                mUIHandler.sendMessage(msg);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {
                        super.onFailure(call, e);
                        Log.e(TAG,"testMyNetLibGetPhoto onFailure="+e);
                    }
                });

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

                    @Override
                    public void onFailure(Call call, IOException e) {
                        super.onFailure(call, e);
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

    private void notifyLifeCycle(String lifeEvent, String msg) {
        Log.d(TAG,"notifyLifeCycle lifeEvent="+lifeEvent+", msg="+ msg);
        int len = DataController.INSTANCE.getIpcCallBackList().beginBroadcast();
        for (int i = 0; i <len ; i++) {
            try {
                ICommonCallback callback = DataController.INSTANCE.getIpcCallBackList().getBroadcastItem( i);
                if(callback!=null){
                    callback.onEvent(lifeEvent, msg);
                }
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            finally {
                DataController.INSTANCE.getIpcCallBackList().finishBroadcast();
            }
        }
    }
}
