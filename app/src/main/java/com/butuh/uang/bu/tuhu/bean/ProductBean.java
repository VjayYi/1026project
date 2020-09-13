package com.butuh.uang.bu.tuhu.bean;

import java.security.PrivateKey;
import java.util.List;
import java.util.PrimitiveIterator;

public class ProductBean {

    /**
     * serialNumber : 产品ID
     * counterMark : 产品标签
     * openMode : 是否优化 1 否 2 是
     * designation : 产品名
     * tagline : 宣传语
     * showMinimumAmount : 显示贷款最小金额
     * displayLargestAmountOfMoney : 显示贷款最大金额
     * tampilkanPeriodeMinimum : 显示贷款最小周期
     * displayMaximumCycle : 显示贷款最大周期
     * showSymbolPeriod : 显示贷款周期单位, 1日 2周 3月 4年
     * displayTheDefaultValue : 显示默认贷款金额
     * showTheDefaultCycle : 显示默认贷款周期
     * showLoanCycle : 显示贷款 利率
     * revealCoverCharge : 显示贷款 服务费
     * displayTheLoanTime : 最快放款时长(小时)
     * giveAMarkWithAppStore : 应用商品评分
     * peringkatKeseluruhan : 综合评分
     * throughoutScore : 通过率评分
     * loanRateScore : 放款速度评分
     * handlingFeeScore : 手续费评分
     * create : 创建时间
     * regenerate : 更新时间
     * Characteristic : 产品logo
     * site : 产品URL
     * tipe : 产品类型, 1:GooglePlay, 2:Apk, 3:API, 4:H5
     * packet : 产品包名
     * favorited : 是否收藏 1 是 0 否
     */

    private String serialNumber;
    private String productId;
    private String counterMark;
    private int openMode;
    private String designation;
    private String tagline;
    private String showMinimumAmount;
    private String displayLargestAmountOfMoney;
    private String tampilkanPeriodeMinimum;
    private String displayMaximumCycle;
    private String showSymbolPeriod;
    private String displayTheDefaultValue;
    private String showTheDefaultCycle;
    private String showLoanCycle;
    private String revealCoverCharge;
    private String displayTheLoanTime;
    private String giveAMarkWithAppStore;
    private String peringkatKeseluruhan;
    private String throughoutScore;
    private String loanRateScore;
    private String handlingFeeScore;
    private String create;
    private String regenerate;
    private String Characteristic;
    private String site;
    private int tipe;
    private String packet;
    private int favorited;
    private List<AdditionalBean> additional;
    private List<LimitsBean> limits;
    private long historyTime;

    public long getHistoryTime() {
        return historyTime;
    }

    public void setHistoryTime(long historyTime) {
        this.historyTime = historyTime;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public List<AdditionalBean> getAdditional() {
        return additional;
    }

    public void setAdditional(List<AdditionalBean> additional) {
        this.additional = additional;
    }

    public List<LimitsBean> getLimits() {
        return limits;
    }

    public void setLimits(List<LimitsBean> limits) {
        this.limits = limits;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getCounterMark() {
        return counterMark;
    }

    public void setCounterMark(String counterMark) {
        this.counterMark = counterMark;
    }

    public int getOpenMode() {
        return openMode;
    }

    public void setOpenMode(int openMode) {
        this.openMode = openMode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public String getShowMinimumAmount() {
        return showMinimumAmount;
    }

    public void setShowMinimumAmount(String showMinimumAmount) {
        this.showMinimumAmount = showMinimumAmount;
    }

    public String getDisplayLargestAmountOfMoney() {
        return displayLargestAmountOfMoney;
    }

    public void setDisplayLargestAmountOfMoney(String displayLargestAmountOfMoney) {
        this.displayLargestAmountOfMoney = displayLargestAmountOfMoney;
    }

    public String getTampilkanPeriodeMinimum() {
        return tampilkanPeriodeMinimum;
    }

    public void setTampilkanPeriodeMinimum(String tampilkanPeriodeMinimum) {
        this.tampilkanPeriodeMinimum = tampilkanPeriodeMinimum;
    }

    public String getDisplayMaximumCycle() {
        return displayMaximumCycle;
    }

    public void setDisplayMaximumCycle(String displayMaximumCycle) {
        this.displayMaximumCycle = displayMaximumCycle;
    }

    public String getShowSymbolPeriod() {
        return showSymbolPeriod;
    }

    public void setShowSymbolPeriod(String showSymbolPeriod) {
        this.showSymbolPeriod = showSymbolPeriod;
    }

    public String getDisplayTheDefaultValue() {
        return displayTheDefaultValue;
    }

    public void setDisplayTheDefaultValue(String displayTheDefaultValue) {
        this.displayTheDefaultValue = displayTheDefaultValue;
    }

    public String getShowTheDefaultCycle() {
        return showTheDefaultCycle;
    }

    public void setShowTheDefaultCycle(String showTheDefaultCycle) {
        this.showTheDefaultCycle = showTheDefaultCycle;
    }

    public String getShowLoanCycle() {
        return showLoanCycle;
    }

    public void setShowLoanCycle(String showLoanCycle) {
        this.showLoanCycle = showLoanCycle;
    }

    public String getRevealCoverCharge() {
        return revealCoverCharge;
    }

    public void setRevealCoverCharge(String revealCoverCharge) {
        this.revealCoverCharge = revealCoverCharge;
    }

    public String getDisplayTheLoanTime() {
        return displayTheLoanTime;
    }

    public void setDisplayTheLoanTime(String displayTheLoanTime) {
        this.displayTheLoanTime = displayTheLoanTime;
    }

    public String getGiveAMarkWithAppStore() {
        return giveAMarkWithAppStore;
    }

    public void setGiveAMarkWithAppStore(String giveAMarkWithAppStore) {
        this.giveAMarkWithAppStore = giveAMarkWithAppStore;
    }

    public String getPeringkatKeseluruhan() {
        return peringkatKeseluruhan;
    }

    public void setPeringkatKeseluruhan(String peringkatKeseluruhan) {
        this.peringkatKeseluruhan = peringkatKeseluruhan;
    }

    public String getThroughoutScore() {
        return throughoutScore;
    }

    public void setThroughoutScore(String throughoutScore) {
        this.throughoutScore = throughoutScore;
    }

    public String getLoanRateScore() {
        return loanRateScore;
    }

    public void setLoanRateScore(String loanRateScore) {
        this.loanRateScore = loanRateScore;
    }

    public String getHandlingFeeScore() {
        return handlingFeeScore;
    }

    public void setHandlingFeeScore(String handlingFeeScore) {
        this.handlingFeeScore = handlingFeeScore;
    }

    public String getCreate() {
        return create;
    }

    public void setCreate(String create) {
        this.create = create;
    }

    public String getRegenerate() {
        return regenerate;
    }

    public void setRegenerate(String regenerate) {
        this.regenerate = regenerate;
    }

    public String getCharacteristic() {
        return Characteristic;
    }

    public void setCharacteristic(String Characteristic) {
        this.Characteristic = Characteristic;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public String getPacket() {
        return packet;
    }

    public void setPacket(String packet) {
        this.packet = packet;
    }

    public int getFavorited() {
        return favorited;
    }

    public void setFavorited(int favorited) {
        this.favorited = favorited;
    }
}
