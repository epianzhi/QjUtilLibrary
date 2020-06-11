package com.qj.ulibrary.retrofit.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.qj.ulibrary.retrofit.ConstantUtils;


/**
 * @Description: Toast工具类
 */
public class ToastUtils {
    private volatile static ToastUtils instance;
    private Toast toast = null;
    private Toast toast2 = null;
    private static Context context;
    private LayoutInflater inflater;
    private View layout = null;
    private TextView title;
    private TextView text;
    public static final int MES_ERROR = 0;
    public static final int MES_ALALL = 1;
    public static final int MES_NODATA = 2;

    /**
     * 单例
     *
     * @return
     */
    public static ToastUtils getInstance() {
        if (instance == null) {
            //当变量为空时,同步(将同步写在方法内部,所以此同步方法只会运行一次，提高了效率)
            synchronized (ToastUtils.class) {
                if (instance == null) {
                    instance = new ToastUtils();
                }
            }
        }
        if (context == null) {
            context = ConstantUtils.getAppContext();
        }
        return instance;
    }

    /**
     * @param
     * @return void 返回类型
     * @throws
     * @Title: toastShow
     * @Description: 使Toast不会因多次点击而显示时间过长
     */
    public void toastShow(String text) {
        if (toast == null) {
            toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            toast.setText(text);
        }
        toast.show();
    }


}
