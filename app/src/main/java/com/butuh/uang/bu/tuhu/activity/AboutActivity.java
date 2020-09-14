package com.butuh.uang.bu.tuhu.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.PrivacyBean;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.AppInfoUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_work_time)
    TextView tvWorkTime;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_version)
    TextView tvVersion;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_about;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        pageName="page-about";
        tvVersion.setText(AppInfoUtil.getVersionName());
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void requestOnCreate() {
        loadData();
    }

    @Override
    public void destroy() {

    }


    private void loadData(){
        showLoadingDialog(true);
        String data="[[\"411\",\"2\"],[\"412\",\"437\"],[\"413\",\"about\"]]";
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
                String source=result.getKuantitas().get(0).getDetails();

                tvDescription.setText(Html.fromHtml(source));
            }

            @Override
            public void failure(String code, Throwable throwable) {
                showLoadingDialog(false);
                showToast(throwable.getMessage());
            }
        });
    }
}
