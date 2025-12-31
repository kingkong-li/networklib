package com.example.networklib

import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import android.util.Log

object PermissionChecker{
    private var mPackageManager: PackageManager? = null
   public fun init(context:Context){
       mPackageManager= context.packageManager
   }
    public fun checkPermission(uid:Int):Boolean{
//        if (uid == Process.SYSTEM_UID) {
//            return false
//        }
        val packageName: String? = mPackageManager?.getNameForUid(uid)
        Log.i("PermissionChecker", "packageName:$packageName")

        return true
    }
}