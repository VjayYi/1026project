package com.butuh.uang.bu.tuhu.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import com.butuh.uang.bu.tuhu.app.ProjectApplication;

import java.util.List;

public class AppInfoUtil {

    public static int getVersionCode() {
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
     *
     * @return
     */
    public static String getPackageName() {
        return ProjectApplication.getInstance().getPackageName();
    }

    /**
     * 判断应用是否安装
     *
     * @param pkgName
     * @return
     */
    public static boolean checkAppInstalled(String pkgName) {
        if (pkgName == null || pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = ProjectApplication.getInstance().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            packageInfo = null;
            e.printStackTrace();
        }
        if (packageInfo == null) {
            return false;
        } else {
            return true;//true为安装了，false为未安装
        }
    }

    /**
     * 打开或者安装app
     *
     * @param activity
     * @param packageName
     * @return
     */
    public static void openOrInstallApp(Activity activity, String packageName) {
        try {
            if (checkAppInstalled(packageName)) {
                openOrInstallApp(activity, packageName);
            } else {
                Uri uri = Uri.parse("market://details?id=" + packageName);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//            String googlePlay = "com.android.vending";
//            intent.setPackage(googlePlay);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void openApp(Context context, String packageName) {
//        String mainAct = null;
//        PackageManager pkgMag = context.getPackageManager();
//        Intent intent = new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        intent.setFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        List<ResolveInfo> list = pkgMag.queryIntentActivities(intent,
//                PackageManager.GET_ACTIVITIES);
//        for (int i = 0; i < list.size(); i++) {
//            ResolveInfo info = list.get(i);
//            if (info.activityInfo.packageName.equals(packageName)) {
//                mainAct = info.activityInfo.name;
//                break;
//            }
//        }
//        if (TextUtils.isEmpty(mainAct)) {
//            return;
//        }
//        intent.setComponent(new ComponentName(packageName, mainAct));
//        context.startActivity(intent);
        PackageManager packageManager = context.getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return;
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}
