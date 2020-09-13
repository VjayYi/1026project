package com.butuh.uang.bu.tuhu.http;

import com.butuh.uang.bu.tuhu.util.NetworkUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheInterceptor implements Interceptor {
    public Response intercept(Chain chain) throws IOException {
        Response resp;
        Request req;
        if (NetworkUtil.isThereANet()) {
            //有网络,检查10秒内的缓存
            req = chain.request()
                    .newBuilder()
                    .cacheControl(new CacheControl
                            .Builder()
                            .maxAge(10, TimeUnit.SECONDS)
                            .build())
                    .build();
        } else {
            //无网络,检查30天内的缓存,即使是过期的缓存
            req = chain.request().newBuilder()
                    .cacheControl(new CacheControl.Builder()
                            .onlyIfCached()
                            .maxStale(30, TimeUnit.SECONDS)
                            .build())
                    .build();
        }
        resp = chain.proceed(req);
        return resp.newBuilder().build();
    }
}
