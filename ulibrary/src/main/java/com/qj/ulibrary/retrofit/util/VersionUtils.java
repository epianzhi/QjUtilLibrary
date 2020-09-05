package com.qj.ulibrary.retrofit.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @Description:
 */

public class VersionUtils {
    //volatile关键字确保当instance被初始化为ChineseUtil的实例时，多个线程正确处理instance变量
    private volatile static VersionUtils instance;

    /**
     * 单例
     *
     * @return
     */
    public static VersionUtils getInstance() {
        if (instance == null) {
            //当变量为空时,同步(将同步写在方法内部,所以此同步方法只会运行一次，提高了效率)
            synchronized (VersionUtils.class) {
                if (instance == null) {
                    instance = new VersionUtils();
                }
            }
        }
        return instance;
    }

    //版本名
    public String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    //版本号
    public int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;

        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);

            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
