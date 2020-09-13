package com.butuh.uang.bu.tuhu.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FormatUtil {

    public static String formatMoney(double money){
        DecimalFormat formatter = new DecimalFormat("Rp###,###.##");
        return formatter.format(money)
                .replace(",","-")
                .replace(".",",")
                .replace("-",".");
    }

    public static String formatMoney(int money){
        DecimalFormat formatter = new DecimalFormat("Rp###,###.##");
        return formatter.format(money)
                .replace(",",".");
    }

    public static String formatMoney(String money){
        int data=NumberUtil.pasrInt(money);
        DecimalFormat formatter = new DecimalFormat("Rp###,###.##");
        return formatter.format(data)
                .replace(",",".");
    }

    public static String formatRate(String rate){
        BigDecimal db=new BigDecimal(rate);
        db=db.multiply(new BigDecimal(100));
        String data=NumberUtil.removeInvalidateZero(db.toString());
        return (data+"%").replace(".",",");
    }
}
