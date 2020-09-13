package com.butuh.uang.bu.tuhu.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.butuh.uang.bu.tuhu.app.ProjectApplication;

public class AppInfoUtil {

    public static int getVersionCode(){
        int versionCode = 0;
        try {
            //获取软件版本号，对应AndroidManifest.xml下android:versionCode
            versionCode = ProjectApplication.getInstance().getPackageManager().
                    getPackageInfo(ProjectApplication.getInstance().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }


    /**
     * 获取版本号名称
     *
     * @return
     */
    public static String getVersionName() {
        String verName = "";
        try {
            verName = ProjectApplication.getInstance().getPackageManager().
                    getPackageInfo(ProjectApplication.getInstance().getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return verName;
    }

    /**
     * 获取应用包名
     * @return
     */
    public static String getPackageName(){
        return ProjectApplication.getInstance().getPackageName();
    }

    /**
     * 判断应用是否安装
     * @param pkgName
     * @return
     */
    public static boolean checkAppInstalled(String pkgName) {
        if (pkgName== null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = ProjectApplication.getInstance().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if(packageInfo == null) {
            return false;
        } else {
            return true;//true为安装了，false为未安装
        }
    }
}
