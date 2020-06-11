package com.qj.ulibrary.retrofit.http;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;


import com.qj.ulibrary.retrofit.ConstantUtils;
import com.qj.ulibrary.retrofit.util.LogUtils;
import com.qj.ulibrary.retrofit.util.NetworkUtils;
import com.qj.ulibrary.retrofit.util.ToastUtils;

import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Subscriber;

/**
 *
 */

public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    //    private SimpleLoadDialog dialogHandler;
    protected SweetAlertDialog pDialog;
    protected int tag;//标识
    private Context context;

    public ProgressSubscriber(Context context) {
        this.context = context;
        if (pDialog == null) {
            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("加载中...");
            pDialog.setCancelable(true);
        }

    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }


    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (pDialog != null) {
            pDialog.show();
        }

    }

    @Override
    public void onNext(T t) {
        _onNext(t);
    }

    /**
     * 隐藏Dialog
     */
    protected void dismissProgressDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        e.printStackTrace();
        if (!NetworkUtils.getInstance().isNetworkAvailable(ConstantUtils.getAppContext())) {
            ToastUtils.getInstance().toastShow("请检查网络");
        }

        else {
            if (!e.getMessage().equals("")) {

                if (e instanceof ApiException) {
                    ApiException exception = (ApiException) e;
//                    if (exception.getCode() == 399 || exception.getMessage().equals("登录超时!")) {//登录超时
//                        SPUtils.clear(context);
//                        context.startActivity(new Intent(context, LoginActivity.class));
//                    }
//                    SPUtils.clear(context);
                }

                LogUtils.d("flag", e.getMessage());
                ToastUtils.getInstance().toastShow(e.getMessage());
            }
//            ToastUtils.getInstance().toastShow("请求失败，请稍后再试...");
////            _onError("请求失败，请稍后再试...");
        }
        Log.e("error", e.getMessage());

    }

    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    protected abstract void _onNext(T t);

//    protected abstract void _onError(String message);
}
