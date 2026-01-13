package com.example.networklib

import android.content.Context
import android.content.pm.PackageManager
import android.os.Process
import android.util.Log

object PermissionChecker {
    private const val TAG: String = "PermissionChecker"

    private const val CLIENT_PACKAGE_NAME = "com.jingang.lifechange"
    private var mPackageManager: PackageManager? = null
    fun init(context: Context) {
        mPackageManager = context.packageManager
    }

    fun checkPermission(uid: Int): Boolean {
        // 首次onTransact来自android.uid.system进程
        if (uid == Process.SYSTEM_UID) {
            Log.i(TAG, "checkPermission: uid == Process.SYSTEM_UID")
            return true
        }
        // 获取包名，校验报名是否一致，还可以进一步根据报名获取签名信息来校验签名实现更严格的安全检查
        val packageName: String? = mPackageManager?.getNameForUid(uid)
        Log.i(TAG, "packageName:$packageName")
        return CLIENT_PACKAGE_NAME == packageName
    }
}