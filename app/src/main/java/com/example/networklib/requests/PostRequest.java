package com.example.networklib.requests;

import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Request;

/**
 * @author jingang.li
 */
public class PostRequest extends BaseRequest {
    private String mUrl;
    private FormBody mRequestBody;

    private PostRequest(Builder builder){
        this.mUrl= builder.mUrl;
        this.mRequestBody= builder.mRequestBodyBuild.build();
    }

    @Override
    public Request getRealRequest() {
        Request.Builder builder = new Request.Builder();
        builder.url(mUrl);
        builder.post(mRequestBody);
        return builder.build();
    }

    public static class Builder {
        private final String mUrl;
        private FormBody.Builder mRequestBodyBuild;
        private Map<String, String> mParametersMap;

        public Builder(String  url){
               mUrl=url;
               mParametersMap=new HashMap<>();
               mRequestBodyBuild=new FormBody.Builder();
        }

        public Builder addParameter(String key, String value)
        {
            mParametersMap.put(key,value);
            return this;

        }


        public PostRequest build(){
            if (mParametersMap != null &&
                    !mParametersMap.isEmpty()) {
                for (Map.Entry<String, String> entry : mParametersMap.entrySet()) {
                    mRequestBodyBuild.add(entry.getKey(), entry.getValue());
                }

            } else {
                throw new IllegalArgumentException("mParametersMap is empty!");
            }
            return new PostRequest(this);
        }


    }
}
