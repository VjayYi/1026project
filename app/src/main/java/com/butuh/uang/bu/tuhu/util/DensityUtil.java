package com.butuh.uang.bu.tuhu.util;

import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;

import com.butuh.uang.bu.tuhu.app.ProjectActivityManager;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;

import java.lang.reflect.Method;

public class DensityUtil {

    public static int dp2px(float dpValue) {
        final float scale = ProjectApplication.getInstance().getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int getStatusBarHeight() {
        int result = 0;
        int resourceId = ProjectApplication.getInstance().getApplicationContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = ProjectApplication.getInstance().getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static int getScreenWidth() {
        int realWidth = 0;
        try {
            Display display = ProjectActivityManager.getInstance().currentActivity().getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realWidth = size.x;
            } else if (android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawW = Display.class.getMethod("getRawWidth");
                realWidth = (Integer) mGetRawW.invoke(display);
            } else {
                realWidth = metrics.widthPixels;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realWidth;
    }

    public static int getScreenHeight() {
        int realHeight = 0;
        try {
            Display display = ProjectActivityManager.getInstance().currentActivity().getWindowManager().getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            if (android.os.Build.VERSION.SDK_INT >= 17) {
                Point size = new Point();
                display.getRealSize(size);
                realHeight = size.y;
            } else if (android.os.Build.VERSION.SDK_INT >= 14) {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                realHeight = (Integer) mGetRawH.invoke(display);
            } else {
                realHeight = metrics.heightPixels;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return realHeight;
    }

}