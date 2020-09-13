package com.butuh.uang.bu.tuhu.util;

import android.content.pm.ApplicationInfo;
import android.text.TextUtils;

import com.butuh.uang.bu.tuhu.app.ProjectApplication;

import java.lang.reflect.Field;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

public class CommonUtil {

    /**
     * 签名的计算
     * 参与计算的参数包含"会话ID"、"设备唯一ID"、"时间戳"、"噪音参数"、"APP INFO参数" 和 APP密钥 共6个,
     * 将6个参数按"字典序"排序后合并为一个字符串，计算该字符串的 SHA1 值即签名。
     * 注意，每次请求都要重新计算签名，因为每次请求的"时间戳"和"噪音参数"不同，每次的签名结果也是不同的。
     */
    public static String createSign(Map<String, String> packageParams) {
        StringBuilder stringBuilder = new StringBuilder();
        List<Map.Entry<String, String>> es = sortMapByValue(packageParams);
        for (Map.Entry<String, String> entry : es) {
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"s".equals(k)) {
                stringBuilder.append(v);
            }
        }
        return SHAUtil.SHA1(stringBuilder.toString());
    }

    // 使用 Map按key进行排序
    private static Map<String, String> sortMapByKey(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        Map<String, String> sortMap = new TreeMap<String, String>(new MapKeyComparator());
        sortMap.putAll(map);
        return sortMap;
    }

    // 使用 Map按value进行排序
    private static List<Map.Entry<String, String>> sortMapByValue(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
            @Override
            public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
                return o1.getValue().compareTo(o2.getValue());//正序按照value排序
            }
        });
        /*Map<String, String> sortMap = new TreeMap<String, String>();
        for(Map.Entry<String,String> me:list){
            sortMap.put(me.getKey(),me.getValue());
        }*/
        return list;
    }


    //获取类中的字段
    public static List<Field> getFields(Class<?> cls, Class<?> end) {
        ArrayList list = new ArrayList();
        if (!cls.equals(end)) {
            Field[] fields = cls.getDeclaredFields();
            Field[] var7 = fields;
            int var6 = fields.length;
            for (int var5 = 0; var5 < var6; ++var5) {
                Field superClass = var7[var5];
                list.add(superClass);
            }
            Class var8 = (Class) cls.getGenericSuperclass();
            list.addAll(getFields(var8, end));
        }
        return list;
    }


    public static Map<String, String> getMapParams(Object object) {
        Map<String, String> map = new HashMap<>();
        List<Field> fields = getFields(object.getClass(), Object.class);
        for (Field temp : fields) {
            temp.setAccessible(true);
            try {
                if (temp.get(object) != null && !String.valueOf(temp.get(object)).equals("") && !temp.getName().equals("serialVersionUID")) {
                    map.put(temp.getName(), String.valueOf(temp.get(object)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    // MD5加密
    public static String MD5(String s, boolean isUpperCase) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = s.getBytes();
            // 使用MD5创建MessageDigest对象
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte b : md) {
                str[k++] = hexDigits[b >> 4 & 0xf];
                str[k++] = hexDigits[b & 0xf];
            }
            if (isUpperCase) {
                return new String(str).toUpperCase();
            } else {
                return new String(str).toLowerCase();
            }
        } catch (Exception e) {
            return null;
        }
    }

    // 判断当前应用是否是debug状态
    public static boolean isApkInDebug() {
        try {
            ApplicationInfo info = ProjectApplication.getInstance().getApplicationInfo();
            return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static String keepTwoDecimalPlaces(long value) {
        NumberFormat data = NumberFormat.getCurrencyInstance();
        // 保留两位小数
        data.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        data.setRoundingMode(RoundingMode.DOWN);
        String str = data.format(value);
        str = str.substring(1);
        str = str.trim();
        return str;
    }

    public static String keepTwoDecimalPlaces(double value) {
        NumberFormat data = NumberFormat.getCurrencyInstance();
        // 保留两位小数
        data.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        data.setRoundingMode(RoundingMode.DOWN);
        String str = data.format(value);
        str = str.substring(1);
        str = str.trim();
        return str;
    }

    public static String keepTwoDecimalPlacesWithHalfUp(double value) {
        NumberFormat data = NumberFormat.getCurrencyInstance();
        // 保留两位小数
        data.setMaximumFractionDigits(2);
        // 如果不需要四舍五入，可以使用RoundingMode.DOWN
        data.setRoundingMode(RoundingMode.HALF_UP);
        String str = data.format(value);
        str = str.substring(1);
        str = str.trim();
        return str;
    }

    public static String conversionTimeWithT(String time, String format) {
        if (TextUtils.isEmpty(time) || TextUtils.isEmpty(format)) {
            return "";
        } else {
            if (time.contains("T")) {
                TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formatter.setTimeZone(tz);
                try {
                    Date date = formatter.parse(time);
                    formatter = new SimpleDateFormat(format);
                    return formatter.format(date);
                } catch (ParseException e) {
                    return time;
                }
            } else {
                return time;
            }
        }
    }

    public static boolean isTimeDifferenceValueMoreThanFiveMin(String time1, String time2) {
        if (TextUtils.isEmpty(time1) || TextUtils.isEmpty(time2)) {
            return false;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
            Date date;
            long currentTimeMillis1 = 0, currentTimeMillis2 = 0;

            try {
                date = formatter.parse(time1 + ":00");
                currentTimeMillis1 = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                date = formatter.parse(time2 + ":00");
                currentTimeMillis2 = date.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (currentTimeMillis1 == 0 || currentTimeMillis2 == 0) {
                return false;
            } else {
                if (currentTimeMillis1 <= currentTimeMillis2) {
                    return false;
                } else {
                    long value = (currentTimeMillis1 - currentTimeMillis2) / 1000 / 60;
                    return value >= 5;
                }
            }
        }
    }

    public static long currentTimeMillis(String time) {
        if (TextUtils.isEmpty(time)) {
            return 0;
        } else {
            if (time.contains("T")) {
                TimeZone tz = TimeZone.getTimeZone(TimeZone.getDefault().getID());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                formatter.setTimeZone(tz);
                try {
                    Date date = formatter.parse(time);
                    return date.getTime();
                } catch (ParseException e) {
                    return 0;
                }
            } else {
                return 0;
            }
        }
    }

}