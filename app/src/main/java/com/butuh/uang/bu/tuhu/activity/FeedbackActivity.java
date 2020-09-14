package com.butuh.uang.bu.tuhu.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.ToastUtil;
import com.butuh.uang.bu.tuhu.view.RatingBar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class FeedbackActivity extends BaseActivity {

    @BindView(R.id.ratingBar)
    RatingBar ratingBar;
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.tv_length)
    TextView tvLength;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private int ratingStar=0;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_feedback;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        pageName="page-feedback";
        ratingBar.setStar(0);
    }

    @Override
    public void addListeners() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvLength.setText(etContent.getText().length()+"/300");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ratingBar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float ratingCount) {
                ratingStar=(int)ratingCount;
            }
        });
    }

    @Override
    public void requestOnCreate() {

    }

    @Override
    public void destroy() {

    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if (ratingStar==0&& TextUtils.isEmpty(etContent.getText())){
            showToast(getString(R.string.no_rating_content));
            return;
        }
        submit();
    }

    private void submit(){
        List<List<String>> list=new ArrayList<>();
        List<String> data=new ArrayList<>();
        data.add("420");
        data.add(ratingStar+"");
        list.add(data);
        List<String> data1=new ArrayList<>();
        data1.add("419");
        data1.add(etContent.getText().toString());
        list.add(data1);
        showLoadingDialog(true);
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(list));
        Observable<BaseResult> observable= HttpUtil.createService(Interface.class).feedback(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {
                showLoadingDialog(false);
                finish();
            }

            @Override
            public void failure(String code, Throwable throwable) {
                showLoadingDialog(false);
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });
    }
}
