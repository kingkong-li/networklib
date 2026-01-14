
package com.example.networklib;

/**
 *
 * 客户端通过服务端的注册接口把自己的Binder服务的代理Ibinder给到服务端，
 * 服务端拿到这个IBinder就可以向客户端随时发送数据，实现了双向通信
 * 这个场景下等于客户端也是运行了binder服务，也可以说是服务端
 */
interface ICommonCallback {

   oneway void onEvent(String code, String msg);
}