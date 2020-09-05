package com.qj.ulibrary.retrofit.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

import com.qj.ulibrary.retrofit.ConstantUtils;


/**
 * @Description: 权限检测工具
 */

public class PermissionUtils {
    private static Context mContext;
    //volatile关键字确保当instance被初始化为ChineseUtil的实例时，多个线程正确处理instance变量
    private volatile static PermissionUtils instance;

    /**
     * 单例
     *
     * @return
     */
    public static PermissionUtils getInstance() {
        if (instance == null) {
            //当变量为空时,同步(将同步写在方法内部,所以此同步方法只会运行一次，提高了效率)
            synchronized (PermissionUtils.class) {
                if (instance == null) {
                    instance = new PermissionUtils();
                    mContext = ConstantUtils.getAppContext();
                }
            }
        }
        return instance;
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        return ContextCompat.checkSelfPermission(mContext, permission) ==
                PackageManager.PERMISSION_DENIED;
    }
//    public static boolean checkHasPermission(Activity activity, String permission, int requestCode) {
//        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(activity,
//                permission)) {
//            return true;
//        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
//                    permission)) {
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{permission},
//                        requestCode);
//            } else {
//                ActivityCompat.requestPermissions(activity,
//                        new String[]{permission},
//                        requestCode);
//            }
//        }
//        return false;
//    }
}
