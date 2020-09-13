package com.butuh.uang.bu.tuhu.bean;

import java.util.List;

public class LimitsBean {

    /**
     * number : 金额
     * istilah : ["周期范围","周期范围"]
     */

    private String number;
    private List<String> istilah;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<String> getIstilah() {
        return istilah;
    }

    public void setIstilah(List<String> istilah) {
        this.istilah = istilah;
    }
}
