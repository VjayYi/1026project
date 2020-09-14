package com.butuh.uang.bu.tuhu.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.SharedPreferencesUtil;
import com.facebook.FacebookSdk;
import com.butuh.uang.bu.tuhu.util.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProjectApplication extends Application {

    private static ProjectApplication instance;
    private String sessionid;

    static {
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new com.butuh.uang.bu.tuhu.view.RefreshHeader(context);
            }
        });
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @NonNull
            @Override
            public RefreshFooter createRefreshFooter(@NonNull Context context, @NonNull RefreshLayout layout) {
                return new com.butuh.uang.bu.tuhu.view.RefreshFooter(context);
            }
        });
    }

    public static ProjectApplication getInstance() {
        if (instance == null) {
            instance = new ProjectApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sessionid = UUID.randomUUID().toString();
        instance = this;
        FacebookSdk.setApplicationId("752991955496632");
        FacebookSdk.sdkInitialize(this);

        appStartEvent();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public String getSessionid() {
        return sessionid;
    }

    public boolean isUserLogin() {
        String phone = SharedPreferencesUtil.getStringData("phone");
        if (TextUtils.isEmpty(phone))
            return false;
        else
            return true;
    }

    public String getPhone() {
        return SharedPreferencesUtil.getStringData("phone");
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void appStartEvent(){
        String param="[[\"commence\",\"[]\","+new Date().getTime() +",\"0\"]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<BaseResult> observable= HttpUtil.createService(Interface.class).happenlog(body);
        HttpUtil.httpCallback(ProjectApplication.getInstance(), observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {

            }

            @Override
            public void failure(String code, Throwable throwable) {
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });
    }
}