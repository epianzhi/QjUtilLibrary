package com.qj.ulibrary.retrofit.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @Description: 网络相关工具类

 */
public class NetworkUtils {
    //volatile关键字确保当instance被初始化为ChineseUtil的实例时，多个线程正确处理instance变量
    private volatile static NetworkUtils instance;

    /**
     * 单例
     *
     * @return
     */
    public static NetworkUtils getInstance() {
        if (instance == null) {
            //当变量为空时,同步(将同步写在方法内部,所以此同步方法只会运行一次，提高了效率)
            synchronized (NetworkUtils.class) {
                if (instance == null) {
                    instance = new NetworkUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 检测网络状态
     *
     * @param context
     * @return
     */
    public boolean isNetworkAvailable(Context context) {
        boolean hasWifoCon = false;
        boolean hasMobileCon = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo[] netInfos = cm.getAllNetworkInfo();
        for (NetworkInfo net : netInfos) {

            String type = net.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                if (net.isConnected()) {
                    hasWifoCon = true;
                }
            }

            if (type.equalsIgnoreCase("MOBILE")) {
                if (net.isConnected()) {
                    hasMobileCon = true;
                }
            }
        }
        return hasWifoCon || hasMobileCon;
    }

}
