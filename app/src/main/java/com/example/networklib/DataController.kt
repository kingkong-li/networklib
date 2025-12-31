package com.example.networklib

import android.arch.lifecycle.MutableLiveData
import android.os.RemoteCallbackList

object DataController {
    val welComeData= MutableLiveData<String>()
    val ipcCallBackList:RemoteCallbackList<ICommonCallback> = RemoteCallbackList()
}