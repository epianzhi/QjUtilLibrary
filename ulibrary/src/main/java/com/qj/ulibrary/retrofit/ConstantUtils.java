package com.qj.ulibrary.retrofit;

import android.content.Context;

public class ConstantUtils {

    public static Context appContext;

    public static Context getAppContext() {
        return appContext;
    }

    public static void init(Context context) {
        if (null != context) {
            appContext = context;
        }
    }
}
