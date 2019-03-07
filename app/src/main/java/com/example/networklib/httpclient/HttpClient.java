package com.example.networklib.httpclient;

import com.example.networklib.httpclient.interceptors.DefaultInterceptor;
import com.example.networklib.requests.BaseRequest;
import com.example.networklib.requests.ReadyRequest;

import okhttp3.Cache;
import okhttp3.Dns;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * @author jingang.li
 */
public class HttpClient {

    private final OkHttpClient mOkHttpClient;

    public OkHttpClient getRealClient(){
        return mOkHttpClient;
    }

    private HttpClient(HttpClientBuilder builder) {
       mOkHttpClient= builder.mOkHttpClientBuilder.build();
    }

    public ReadyRequest addRequest(BaseRequest baseRequest){

        return new ReadyRequest(this,baseRequest);


    }



    public static class HttpClientBuilder{

        private final OkHttpClient.Builder mOkHttpClientBuilder;
        public HttpClientBuilder() {
            mOkHttpClientBuilder=new OkHttpClient.Builder();
        }


        public HttpClientBuilder cache(Cache cache){
            mOkHttpClientBuilder.cache(cache);
            return this;
        }
        public HttpClientBuilder addInterceptor(Interceptor interceptor){
            mOkHttpClientBuilder.addInterceptor(interceptor);
            return this;
        }
        public HttpClientBuilder dns(Dns dns){
            mOkHttpClientBuilder.dns(dns);
            return this;
        }

        /**
         * 构建httpClient -相当于设置邮箱
         * 这里应该做一点默认的东西、比如说没有缓存设置就执行默认缓存
         * 没有Dns就执行默认的DNS--DNS默认的但是应该有回调
         * @return  HttpClient对象
         */
        public HttpClient build() {


            return new HttpClient(this);
        }


    }

}
