package com.butuh.uang.bu.tuhu.util;

import android.widget.Toast;

import com.butuh.uang.bu.tuhu.app.ProjectApplication;

public class ToastUtil {

    private static Toast toast;

    public static void showShortToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(ProjectApplication.getInstance().getApplicationContext(), null, Toast.LENGTH_SHORT);
        } else {
            if (toast.getDuration() == Toast.LENGTH_LONG) {
                toast.setDuration(Toast.LENGTH_SHORT);
            }
        }
        toast.setText(resId);
        toast.show();
    }

    public static void showShortToast(CharSequence sequence) {
        if (toast == null) {
            toast = Toast.makeText(ProjectApplication.getInstance().getApplicationContext(), null, Toast.LENGTH_SHORT);
        } else {
            if (toast.getDuration() == Toast.LENGTH_LONG) {
                toast.setDuration(Toast.LENGTH_SHORT);
            }
        }
        toast.setText(sequence);
        toast.show();
    }

    public static void showShortToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(ProjectApplication.getInstance().getApplicationContext(), null, Toast.LENGTH_SHORT);
        } else {
            if (toast.getDuration() == Toast.LENGTH_LONG) {
                toast.setDuration(Toast.LENGTH_SHORT);
            }
        }
        toast.setText(str);
        toast.show();
    }

    public static void showLongToast(int resId) {
        if (toast == null) {
            toast = Toast.makeText(ProjectApplication.getInstance().getApplicationContext(), null, Toast.LENGTH_LONG);
        } else {
            if (toast.getDuration() == Toast.LENGTH_SHORT) {
                toast.setDuration(Toast.LENGTH_LONG);
            }
        }
        toast.setText(resId);
        toast.show();
    }

    public static void showLongToast(CharSequence sequence) {
        if (toast == null) {
            toast = Toast.makeText(ProjectApplication.getInstance().getApplicationContext(), null, Toast.LENGTH_LONG);
        } else {
            if (toast.getDuration() == Toast.LENGTH_SHORT) {
                toast.setDuration(Toast.LENGTH_LONG);
            }
        }
        toast.setText(sequence);
        toast.show();
    }

    public static void showLongToast(String str) {
        if (toast == null) {
            toast = Toast.makeText(ProjectApplication.getInstance().getApplicationContext(), null, Toast.LENGTH_LONG);
        } else {
            if (toast.getDuration() == Toast.LENGTH_SHORT) {
                toast.setDuration(Toast.LENGTH_LONG);
            }
        }
        toast.setText(str);
        toast.show();
    }

}