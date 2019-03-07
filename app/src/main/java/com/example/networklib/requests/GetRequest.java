package com.example.networklib.requests;

import okhttp3.Request;

/**
 * @author jingang.li
 */
public class GetRequest extends BaseRequest {

    private String mUrl;

    public GetRequest(String url){
        mUrl=url;
    }

    @Override
    public Request getRealRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl);
        builder.get();
        return builder.build();
    }
}
