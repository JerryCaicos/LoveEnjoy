package com.mobile.love.enjoy.consume.utils;

import com.mobile.love.enjoy.consume.BaseApplication;
import com.mobile.love.enjoy.consume.custom.toast.BaseToast;

/**
 * Created by JerryCaicos on 2017/1/18.
 */

public class ToastUtils
{
    public static void showMessage(String msg)
    {
        BaseToast.create(BaseApplication.getInstance(), msg, BaseToast.Duration.MEDIUM).show();
    }

    public static void showMessage(int resId)
    {
        BaseApplication application = BaseApplication.getInstance();
        BaseToast.create(application, application.getString(resId), BaseToast.Duration.MEDIUM).show();
    }
}
