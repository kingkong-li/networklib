package com.example.networklib.requests;

import okhttp3.Request;

/**
 * @author jingang.li
 */
public abstract class BaseRequest {
    /**
     * 返回Request参数
     *
     * @return request
     */
    public abstract Request getRealRequest();
}
