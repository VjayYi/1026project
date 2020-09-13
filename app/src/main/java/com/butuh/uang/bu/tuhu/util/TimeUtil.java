package com.butuh.uang.bu.tuhu.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;

public class TimeUtil {

    public static String format(@NonNull String dateFormat, Date date) {
        if (TextUtils.isEmpty(dateFormat) || date == null) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            return format.format(date);
        }
    }

    public static String format(@NonNull String dateFormat, Calendar calendar) {
        if (TextUtils.isEmpty(dateFormat) || calendar == null) {
            return null;
        } else {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            return format.format(calendar);
        }
    }

    public static String format(@NonNull String dateFormat, long timeMillis) {
        if (TextUtils.isEmpty(dateFormat)) {
            return null;
        } else {
            Date date = new Date(timeMillis);
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            return format.format(date);
        }
    }

    public static String format(@NonNull String dateFormat, String time) {
        if (TextUtils.isEmpty(dateFormat) || TextUtils.isEmpty(time)) {
            return time;
        } else {
            try {
                SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                Date date = format.parse(time);
                return format(dateFormat, date);
            } catch (ParseException e) {
                e.printStackTrace();
                return time;
            }
        }
    }

    public static String getTimeDiff(@NonNull String dateFormat, long timeMillis) {
        long diff = (Calendar.getInstance().getTimeInMillis() - timeMillis) / 1000;
        if (diff > 365 * 24 * 60 * 60) {
            return format(dateFormat, timeMillis);
        } else if (diff > 24 * 60 * 60) {
            return (int) (diff / (24 * 60 * 60)) + "天前";
        } else if (diff > 60 * 60) {
            return (int) (diff / (60 * 60)) + "小时前";
        } else if (diff > 60) {
            return (int) (diff / (60)) + "分前";
        } else if (diff > 1) {
            return diff + "秒前";
        } else {
            return "刚刚";
        }
    }

}