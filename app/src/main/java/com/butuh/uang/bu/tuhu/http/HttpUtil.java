package com.butuh.uang.bu.tuhu.http;

import android.app.Dialog;
import android.content.Context;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.common.Constants;
import com.butuh.uang.bu.tuhu.param.HeaderParam;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.CommonUtil;
import com.butuh.uang.bu.tuhu.util.NetworkUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;

import java.io.IOException;
import java.lang.reflect.Field;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtil {

    public static <T> T createService(Class<T> clazz) {
        return buildRetrofit().create(clazz);
    }

    public static <T> T createService(String url, Class<T> clazz) {
        return buildRetrofit(url).create(clazz);
    }

    private static Retrofit buildRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new Retrofit
                .Builder()
                .client(getUnsafeOkHttpClient(logging))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .baseUrl(Api.URL)
                .build();
    }

    private static Retrofit buildRetrofit(String url) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new Retrofit
                .Builder()
                .client(getUnsafeOkHttpClient(logging))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .baseUrl(url)
                .build();
    }

    static OkHttpClient getUnsafeOkHttpClient(HttpLoggingInterceptor logging) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new java.security.cert.X509Certificate[0];
                }
            }};

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {

                            HeaderParam param = new HeaderParam();
//                            param.toJson();
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("sessionid", param.getSessionid())
                                    .addHeader("d", param.getD())
                                    .addHeader("tt", param.getTt())
                                    .addHeader("once", param.getOnce())
                                    .addHeader("p", param.getP())
                                    .addHeader("s", param.getS())
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(logging).build();


            if (CommonUtil.isApkInDebug()) {
                okHttpClient = okHttpClient.newBuilder()
                        .sslSocketFactory(sslSocketFactory)
                        .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .addInterceptor(logging)
                        .build();
            } else {
                okHttpClient = okHttpClient.newBuilder()
                        .sslSocketFactory(sslSocketFactory)
                        .hostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER)
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(60, TimeUnit.SECONDS)
                        .retryOnConnectionFailure(true)
//                        .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                        .addInterceptor(new CacheInterceptor())
//                        .addNetworkInterceptor(new CacheNetworkInterceptor())
//                        .cache(new Cache(new File(App.app.externalCacheDir, "ok-cache"), 1024 * 1024 * 30L))
                        .build();
            }

            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void httpCallback(RxAppCompatActivity activity, Observable observable, HttpCallback callback) {
        if (NetworkUtil.isThereANet()) {
            if (activity != null && observable != null && callback != null) {
                observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(activity.bindUntilEvent(ActivityEvent.DESTROY))
                        .subscribe(new Observer<BaseResult>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(BaseResult baseResult) {
                                if (baseResult != null) {
                                    if (baseResult.getStatus().equals("0")) {
                                        if (!activity.isDestroyed() && !activity.isFinishing()) {
                                            callback.success(baseResult.getData(), baseResult.getMessage());
                                        }
                                    } else {
                                        if (!activity.isDestroyed() && !activity.isFinishing()) {
                                            callback.failure(baseResult.getStatus(), new Throwable(baseResult.getMessage() == null ? "" : baseResult.getMessage()));
                                        }
                                    }
                                } else {
                                    if (!activity.isDestroyed() && !activity.isFinishing()) {
                                        callback.success(null, null);
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!activity.isDestroyed() && !activity.isFinishing()) {
                                    callback.failure("99", e);
                                }
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        } else {
            if (callback != null) {
                callback.failure("99", new Throwable(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.no_network)));
            }
        }
    }

    public static void httpCallback(Context context, Observable observable, HttpCallback callback) {
        if (NetworkUtil.isThereANet()) {
            if (context != null && observable != null && callback != null) {
                observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseResult>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(BaseResult baseResult) {
                                if (baseResult != null) {
                                    if (baseResult.getStatus().equals("0")) {
                                        callback.success(baseResult.getData(), baseResult.getMessage());
                                    } else {
                                        callback.failure(baseResult.getStatus(), new Throwable(baseResult.getMessage() == null ? "" : baseResult.getMessage()));
                                    }
                                } else {
                                    callback.success(null, null);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.failure("99", e);
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        } else {
            if (callback != null) {
                callback.failure("99", new Throwable(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.no_network)));
            }
        }
    }

    public static void httpCallback(RxFragment fragment, Observable observable, HttpCallback callback) {
        if (NetworkUtil.isThereANet()) {
            if (fragment != null && observable != null && callback != null) {
                observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(fragment.bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                        .subscribe(new Observer<BaseResult>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(BaseResult baseResult) {
                                if (baseResult != null) {
                                    if (baseResult.getStatus().equals("0")) {
                                        if (!fragment.isDetached() && !fragment.isRemoving()) {
                                            callback.success(baseResult.getData(), baseResult.getMessage());
                                        }
                                    } else {
                                        if (!fragment.isDetached() && !fragment.isRemoving()) {
                                            callback.failure(baseResult.getStatus(), new Throwable(baseResult.getMessage() == null ? "" : baseResult.getMessage()));
                                        }
                                    }
                                } else {
                                    if (!fragment.isDetached() && !fragment.isRemoving()) {
                                        callback.success(null, null);
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!fragment.isDetached() && !fragment.isRemoving()) {
                                    callback.failure("99", e);
                                }
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        } else {
            if (callback != null) {
                callback.failure("99", new Throwable(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.no_network)));
            }
        }
    }


    public static void httpCallback(Dialog dialog, Observable observable, HttpCallback callback) {
        if (NetworkUtil.isThereANet()) {
            if (dialog != null && observable != null && callback != null) {
                observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Observer<BaseResult>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                if (!dialog.isShowing()) {
                                    d.dispose();
                                }
                            }

                            @Override
                            public void onNext(BaseResult baseResult) {
                                if (!dialog.isShowing()) {
                                    if (baseResult != null) {
                                        if (baseResult.getStatus().equals("0")) {
                                            callback.success(baseResult.getData(), baseResult.getMessage());
                                        } else {
                                            callback.failure(baseResult.getStatus(), new Throwable(baseResult.getMessage() == null ? "" : baseResult.getMessage()));
                                        }
                                    } else {
                                        callback.success(null, null);
                                    }
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!dialog.isShowing()) {
                                    callback.failure("99", e);
                                }
                            }

                            @Override
                            public void onComplete() {
                            }
                        });
            }
        } else {
            if (callback != null) {
                callback.failure("99", new Throwable(ProjectApplication.getInstance().getApplicationContext().getResources().getString(R.string.no_network)));
            }
        }
    }

    private static List<Field> getFields(Class<?> cls, Class<?> end) {
        List<Field> list = new ArrayList<Field>();
        if (!cls.equals(end)) {
            Field[] fields = cls.getDeclaredFields();
            Collections.addAll(list, fields);
            Class<?> superClass = (Class<?>) cls.getGenericSuperclass();
            list.addAll(getFields(superClass, end));
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

    public static Map<String, Object> getPostMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        List<Field> fields = getFields(object.getClass(), Object.class);
        for (Field temp : fields) {
            temp.setAccessible(true);
            try {
                if (temp.get(object) != null && !temp.getName().equals("serialVersionUID")) {
                    map.put(temp.getName(), String.valueOf(temp.get(object)));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

}