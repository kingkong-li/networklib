package com.example.networklib.requests;

import com.example.networklib.calllbacks.BaseCallback;
import com.example.networklib.httpclient.HttpClient;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 准备好待发送的Request
 * 相当于把Request信件放入邮箱、加上邮箱设置状态的信件。
 * @author jingang.li
 */
public class ReadyRequest {
    private HttpClient mHttpClient;
    private BaseRequest mRequest;

    public ReadyRequest(HttpClient httpClient, BaseRequest baseRequest) {
        mHttpClient=httpClient;
        mRequest=baseRequest;
    }
    public void sendRequest(final BaseCallback callback){
        mHttpClient.getRealClient().newCall(mRequest.getRealRequest()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call,e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                callback.onResponse(call,response);

            }
        });

    }


}
