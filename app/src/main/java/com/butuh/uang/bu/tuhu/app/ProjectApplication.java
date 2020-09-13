package com.butuh.uang.bu.tuhu.app;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.butuh.uang.bu.tuhu.util.SharedPreferencesUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.util.UUID;

import androidx.annotation.NonNull;

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
        if (instance==null){
            instance=new ProjectApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sessionid= UUID.randomUUID().toString();
        instance = this;

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public String getSessionid() {
        return sessionid;
    }

    public boolean isUserLogin(){
        String phone= SharedPreferencesUtil.getStringData("phone");
        if (TextUtils.isEmpty(phone))
            return false;
        else
            return true;
    }

    public String getPhone(){
        return SharedPreferencesUtil.getStringData("phone");
    }
}