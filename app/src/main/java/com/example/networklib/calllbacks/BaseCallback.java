package com.example.networklib.calllbacks;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * @author jingang.li
 */
public abstract class BaseCallback  {

    /**
     * 失败回调
     * @param call ready request
     * @param e IOException
     */
    public abstract void onFailure(Call call, IOException e);

    /**
     * 成功回调
     * @param call ready request
     * @param response  response
     */
    public abstract void onResponse(Call call, Response response);
}
