package com.butuh.uang.bu.tuhu.util;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;

import java.util.Date;
import java.util.UUID;

import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class GoogleDownloadEvent {

    /**
     * 跳转Google
     * @param context
     * @param bean
     */
    public void openOrDownload(Context context, ProductBean bean){
        if (bean==null)
            return;
        //否则下载
        if (AppInfoUtil.checkAppInstalled(bean.getPacket())){
            //打开应用
            Intent LaunchIntent = context.getPackageManager().getLaunchIntentForPackage(bean.getPacket());
            context.startActivity(LaunchIntent);
        }else{
            //上传跳转点击事件
            uploadGoogleDownloadEvent(context,bean.getSite());
            //todo...跳转Google市场
        }
    }

    /**
     * Google跳转事件上传
     * @param context
     * @param googleUrl
     */
    private void uploadGoogleDownloadEvent(Context context,String googleUrl){
        String param="[[\"upfront\",\"[\""+UUID.randomUUID().toString()+"\", \""+googleUrl+"\"]\","+new Date().getTime() +",0]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<BaseResult> observable= HttpUtil.createService(Interface.class).happenlog(body);
        HttpUtil.httpCallback(context, observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {

            }

            @Override
            public void failure(String code, Throwable throwable) {
            }
        });
    }

}
