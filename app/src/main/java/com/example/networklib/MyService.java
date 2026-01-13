package com.example.networklib;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {
    public static final String TAG = "MyService";
    private final Binder mBinder = new CustomBinderInterface();
    public MyService() {
        super();
        Log.d(TAG,"MyService");

    }

    @Override
    public void onCreate() {
        super.onCreate();
        PermissionChecker.INSTANCE.init( this);
    }


    @Override
    public IBinder onBind(Intent intent) {
        return  mBinder;
    }
    private static class CustomBinderInterface extends ICommonInterface.Stub{
        @Override
        public void set(String data) throws RemoteException {
            Log.d(TAG,"set data="+data);
            DataController.INSTANCE.getWelComeData().postValue(data);

        }

        @Override
        public String get() throws RemoteException {
            Log.d(TAG,"get data");
            return DataController.INSTANCE.getWelComeData().getValue();
        }

        @Override
        public void registerCallback(ICommonCallback callback) throws RemoteException {
            Log.d(TAG,"registerCallback");
            DataController.INSTANCE.getIpcCallBackList().register(callback);

        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            // 这里增加拦截
            int uid = Binder.getCallingUid();
            if(!PermissionChecker.INSTANCE.checkPermission(uid)){
                Log.i(TAG,"checkPermission, result is false, do noting");
                return false;
            }

            return super.onTransact(code, data, reply, flags);
        }
    };


}