package com.butuh.uang.bu.tuhu.util;

import java.math.BigDecimal;

public class NumberUtil {

    public static int pasrInt(String time) {
        if (time == null || time.replace(" ", "").equals("")) {
            return 0;
        } else {
            try {
                return Integer.parseInt(time);
            } catch (Exception e) {
                return 0;
            }
        }
    }

    public static Long pasrLong(String time) {
        if (time == null || time.replace(" ", "").equals("")) {
            return 0l;
        } else {
            try {
                return Long.parseLong(time);
            } catch (Exception e) {
                return 0l;
            }
        }
    }

    public static Double pasrDouble(String time) {
        if (time == null || time.replace(" ", "").equals("")) {
            return 0d;
        } else {
            try {
                return Double.parseDouble(time);
            } catch (Exception e) {
                return 0d;
            }
        }
    }

    public static Float pasrFloat(String time) {
        if (time == null || time.replace(" ", "").equals("")) {
            return 0f;
        } else {
            try {
                return Float.parseFloat(time);
            } catch (Exception e) {
                return 0f;
            }
        }
    }


    public static String removeInvalidateZero(String s) {
        if (s == null) {
            return "0";
        }
        if (s.indexOf(".") > 0) {
            //正则表达
            s = s.replaceAll("0+?$", "");//去掉后面无用的零
            s = s.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
        }
        if (s.trim().length() == 0) {
            s = "0";
        }
        return s;
    }

    public static String removeInvalidateZero(float s, int a) {
        return removeInvalidateZero(s+"",a);
    }

    public static String removeInvalidateZero(double s, int a) {
        return removeInvalidateZero(s+"",a);
    }

    public static String removeInvalidateZero(String s, int a) {
        if (s == null || s.trim().length() == 0)
            return "0";
        if (a < 0)
            a = 0;
        try {
            s = new BigDecimal(s).setScale(a, BigDecimal.ROUND_HALF_UP).toPlainString();
        } catch (Exception e) {
            return s;
        }
//        return s;
        return removeInvalidateZero(s);
    }
}
