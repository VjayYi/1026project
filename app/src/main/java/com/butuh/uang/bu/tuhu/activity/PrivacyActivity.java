package com.butuh.uang.bu.tuhu.activity;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.PrivacyBean;
import com.butuh.uang.bu.tuhu.callback.WebLoadedCallback;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.ToastUtil;

import butterknife.BindView;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PrivacyActivity extends BaseActivity implements WebLoadedCallback{

    @BindView(R.id.web)
    WebView webView;


    private WebViewClient client;
    private String source;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_privacy;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        pageName="page-privacy";
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void requestOnCreate() {
        loadPrivacy();
    }

    @Override
    public void destroy() {
        if (webView != null) {
            webView.removeAllViews();
            webView.removeAllViewsInLayout();
            ViewParent parent = webView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(webView);
            }
            webView.stopLoading();
            webView.clearFormData();
            webView.clearHistory();
            webView.destroy();
            webView = null;
        }
        client = null;
    }

    @Override
    public void onLoad(String title) {

    }

    @Override
    public void onProgress() {

    }

    @Override
    public void onFailed(String s) {
        ToastUtil.showShortToast( s);
    }

    private void initWebViewClient() {
        client = new MyWebViewClient(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);    //支持JavaScript
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        settings.setLoadWithOverviewMode(false);
        settings.setSupportZoom(false); //支持缩放
        settings.setBuiltInZoomControls(false);
        webView.setWebViewClient(client);
        webView.loadDataWithBaseURL(null, source, "text/html", "utf-8", null);
        /*if (type > 0) {
            webView.loadDataWithBaseURL(null, source, "text/html", "utf-8", null);
            tvTitle.setText(TextUtils.isEmpty(title) ? "" : title);
        } else {
            if (mID != 0) {
                getSystemMessage();
            } else {
                if (!TextUtils.isEmpty(source)) {
                    *//*if (source.startsWith("www")) {
                        source = "http://" + source;
                    }*//*
                    webView.loadUrl(WebUrlUtil.convertKeywordLoadOrSearch(source));
                }
            }
        }*/
    }

    private void loadPrivacy(){
        showLoadingDialog(true);
        String data="[[\"411\",\"2\"],[\"412\",\"437\"],[\"413\",\"privacy-policy\"]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), data);
        Observable<BaseResult<PageTableBean<PrivacyBean>>> observable= HttpUtil.createService(Interface.class).accessCatalog(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback<PageTableBean<PrivacyBean>>() {
            @Override
            public void success(PageTableBean<PrivacyBean> result, String message) {
                showLoadingDialog(false);
                if (result==null||result.getKuantitas()==null||result.getKuantitas().size()==0){
                    return;
                }
                if(result.getKuantitas().get(0)==null)
                    return;
                source=result.getKuantitas().get(0).getDetails();
                initWebViewClient();
            }

            @Override
            public void failure(String code, Throwable throwable) {
                showLoadingDialog(false);
                showToast(throwable.getMessage());
            }
        });
    }


    static class MyWebViewClient extends WebViewClient {
        private WebLoadedCallback mCallback;

        public MyWebViewClient() {

        }

        public MyWebViewClient(WebLoadedCallback callback) {
            mCallback = callback;
        }


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            view.loadUrl(Build.VERSION.SDK_INT > 21 ?
                    request.getUrl().toString() :
                    request.toString());
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mCallback != null) {
                mCallback.onProgress();
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (mCallback != null) {
                mCallback.onLoad(view.getTitle());
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            try {
                String javascript = "javascript:function ResizeImages() {" +
                        "var myimg,oldwidth;" +
                        "var maxwidth = document.body.clientWidth;" +
                        "for(i=0;i <document.images.length;i++){" +
                        "myimg = document.images[i];" +
                        "if(myimg.width > maxwidth){" +
                        "oldwidth = myimg.width;" +
                        "myimg.width = maxwidth;" +
                        "}" +
                        "}" +
                        "}";
                view.loadUrl(javascript);
                view.loadUrl("javascript:ResizeImages();");
            }catch (Exception e){
                e.printStackTrace();
            }
        }


        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mCallback != null) {
                    mCallback.onFailed(error.getDescription().toString());
                }
            } else {
                if (mCallback != null) {
                    mCallback.onFailed(request.toString());
                }
            }
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            super.onReceivedHttpError(view, request, errorResponse);

        }
    }

}
