package com.butuh.uang.bu.tuhu.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class KeyboardUtil {

    /**
     * 隐藏输入法
     *
     * @param v
     */
    public static void hideIme(Context mContext,View v) {
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(
                    v.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 显示输入法
     *
     * @param v 与输入法关联的view
     */
    public static void showKeyboard(Context mContext,View v) {
        InputMethodManager imm = (InputMethodManager)
                mContext.getSystemService(
                        Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

}
