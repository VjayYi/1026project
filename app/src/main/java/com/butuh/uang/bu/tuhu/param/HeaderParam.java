package com.butuh.uang.bu.tuhu.param;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.util.AppInfoUtil;
import com.butuh.uang.bu.tuhu.util.CommonUtil;
import com.butuh.uang.bu.tuhu.util.SharedPreferencesUtil;
import com.google.gson.Gson;

import java.util.Date;
import java.util.UUID;

public class HeaderParam {

    //秘钥
    private String secretKey="tC1aG0eC1dI1eG0e";

    //会话ID, 每次APP启动时生成，切勿保存。格式：/[a-zA-Z0-9\-_]{16,32}/
    private String sessionid;

    //设备唯一ID,（重要KEY，切记保证唯一） 格式：/[a-zA-Z0-9\-_]{16,32}/
    private String d;

    //时间戳, int 类型 最新的时间。（客户端时间与服务器时间差距超过30分钟的请求请使用校准时间）
    private String tt;

    //噪音参数, 随机字符串，随机内容为 [a-zA-Z0-9]{16,32}
    private String once;

    /**
     * 先将内容组合为 "打包渠道名:包名(版本名称 <版本号>)" 格式的字符串，
     * 再对该字符串进行base64编码,
     * 最后将编号后的字符串中的'+','/','='分别替换为'-','_','', 得到最终字符串。
     */
    private String p;

    //签名参数原始值 未经过 sha1
    /**
     * 签名的计算
     * 参与计算的参数包含"会话ID"、"设备唯一ID"、"时间戳"、"噪音参数"、"APP INFO参数" 和 APP密钥 共6个,
     * 将6个参数按"字典序"排序后合并为一个字符串，计算该字符串的 SHA1 值即签名。
     * 注意，每次请求都要重新计算签名，因为每次请求的"时间戳"和"噪音参数"不同，每次的签名结果也是不同的。
     */
    private String s;

    public HeaderParam() {
        p=getP();
        tt = String.valueOf(new Date().getTime());
        once=UUID.randomUUID().toString();
        d= SharedPreferencesUtil.getStringData("deviceId");
        //application每次启动生成
        sessionid= ProjectApplication.getInstance().getSessionid();
        if (TextUtils.isEmpty(d)){
            //设备id本地只记录一次，卸载重装才重新生成
            d= UUID.randomUUID().toString();
            SharedPreferencesUtil.saveData("deviceId",d);
        }
        s= CommonUtil.createSign(CommonUtil.getMapParams(this));
    }


    public String getP(){
        //base64UrlEncode("打包渠道名:包名(版本名称 <版本号>)")
        //googlePlay:com.butuh.uang.bu.tuhu(1.0<1>)
        String data="googlePlay:"+ AppInfoUtil.getPackageName() +"("+AppInfoUtil.getVersionName()+"<"+AppInfoUtil.getVersionCode()+">)";
        String base64=Base64.encodeToString(data.getBytes(),Base64.NO_WRAP);
        base64=base64.replaceAll("\\+","-");
        base64=base64.replaceAll("/","_");
        base64=base64.replaceAll("=","");
        Log.e("LOG",base64);
        return base64;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public String getD() {
        return d;
    }

    public void setD(String d) {
        this.d = d;
    }

    public String getTt() {
        return tt;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public String getOnce() {
        return once;
    }

    public void setOnce(String once) {
        this.once = once;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public void toJson(){
        Log.e("Headers",new Gson().toJson(this).toString());
    }
}
