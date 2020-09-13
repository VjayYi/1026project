package com.butuh.uang.bu.tuhu.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SharedPreferencesUtil {

    /**
     * @param key   键
     * @param value 值
     */
    public static void saveData(String key, String value) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void saveData(String key, int value) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void saveData(String key, long value) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void saveData(String key, float value) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * @param key   键
     * @param value 值
     */
    public static void saveData(String key, boolean value) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * @param key 键
     */
    public static String getStringData(String key) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * @param key 键
     */
    public static int getIntData(String key) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    /**
     * @param key 键
     */
    public static long getLongData(String key) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        return sharedPreferences.getLong(key, 0);
    }

    /**
     * @param key 键
     */
    public static float getFloatData(String key) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 0);
    }

    /**
     * @param key 键
     */
    public static double getDoubleData(String key) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        float f = sharedPreferences.getFloat(key, 0);
        if (f == 0) {
            return 0;
        } else {
            BigDecimal b = new BigDecimal(String.valueOf(f));
            return b.doubleValue();
        }
    }

    /**
     * @param key 键
     */
    public static boolean getBooleanData(String key) {
        SharedPreferences sharedPreferences = ProjectApplication.getInstance().getApplicationContext().getSharedPreferences(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.app_name), Activity.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }


    public static void saveHistory(ProductBean bean){
        String history=getStringData("history");
        if (TextUtils.isEmpty(history)){
            List<ProductBean> list=new ArrayList<>();
            list.add(bean);
            saveData("history",GsonTools.createGsonString(list));
        }else{
            List<ProductBean> list=GsonTools.changeGsonToList(history,ProductBean.class);
            boolean isFinder=false;
            bean.setHistoryTime(new Date().getTime());
            for(ProductBean p:list){
                if (p.getSerialNumber().equals(bean.getSerialNumber())){
                    isFinder=true;
                    break;
                }
            }
            if (!isFinder){
                list.add(bean);
            }
            saveData("history",GsonTools.createGsonString(list));
        }
    }

    public static List<ProductBean> getHistory(){
        String history=getStringData("history");
        List<ProductBean> list=new ArrayList<>();
        if (!TextUtils.isEmpty(history)){
            list = GsonTools.changeGsonToList(history, ProductBean.class);
        }
        //排序规则，这里是以历史事件排序
        Comparator<ProductBean> comparator = new Comparator<ProductBean>() {
            public int compare(ProductBean s1, ProductBean s2) {
                return (int) (s2.getHistoryTime() - s1.getHistoryTime());
            }
        };

        //这里就会自动根据规则进行排序
        Collections.sort(list,comparator);
        return list;
    }

}