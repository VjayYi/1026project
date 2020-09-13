package com.butuh.uang.bu.tuhu.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.butuh.uang.bu.tuhu.app.ProjectApplication;

import androidx.appcompat.app.AppCompatActivity;

public class NetworkUtil {

    /**
     * 网络不可用
     */
    public static final int NO_NET_WORK = 0;

    /**
     * 是wifi连接
     */
    public static final int WIFI = 1;

    /**
     * 不是wifi连接
     */
    public static final int NO_WIFI = 2;

    // 判断是否打开网络
    public static boolean isNetWorkAvailable() {
        boolean isAvailable = false;
        ConnectivityManager cm = (ConnectivityManager) ProjectApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            isAvailable = true;
        }
        return isAvailable;
    }

    // 获取网络类型
    public static int getNetWorkType() {
        if (!isNetWorkAvailable()) {
            return NetworkUtil.NO_NET_WORK;
        }
        ConnectivityManager cm = (ConnectivityManager) ProjectApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        // cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting()) {
            return NetworkUtil.WIFI;
        } else {
            return NetworkUtil.NO_WIFI;
        }
    }

    // 判断当前网络是否为wifi
    @SuppressWarnings("static-access")
    public static boolean isWiFiConnected() {
        ConnectivityManager manager = (ConnectivityManager) ProjectApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo.getType() == manager.TYPE_WIFI;
    }

    // 判断MOBILE网络是否可用
    public static boolean isMobileDataEnable() {
        ConnectivityManager manager = (ConnectivityManager) ProjectApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable = false;
        isMobileDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return isMobileDataEnable;
    }

    // 判断wifi 是否可用
    public static boolean isWifiDataEnable() {
        ConnectivityManager manager = (ConnectivityManager) ProjectApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable = false;
        isWifiDataEnable = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }

    // 判断是否有网
    public static boolean isThereANet() {
        if (isNetWorkAvailable()) {
            if (getNetWorkType() == NetworkUtil.WIFI) {
                return isWifiDataEnable();
            } else if (getNetWorkType() == NetworkUtil.NO_WIFI) {
                return isMobileDataEnable();
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    // 跳转到网络设置页面
    public static void GoSetting(AppCompatActivity activity) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivity(intent);
    }

    // 打开网络设置界面
    public static void openSetting(AppCompatActivity activity) {
        Intent intent = new Intent("/");
        ComponentName cn = new ComponentName("com.android.settings", "com.android.settings.WirelessSettings");
        intent.setComponent(cn);
        intent.setAction("android.intent.action.VIEW");
        activity.startActivityForResult(intent, 0);
    }

}