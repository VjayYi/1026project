package com.butuh.uang.bu.tuhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.KeyboardUtil;
import com.butuh.uang.bu.tuhu.util.SharedPreferencesUtil;
import com.butuh.uang.bu.tuhu.view.MyToast;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.tv_phone_code)
    TextView tvPhoneCode;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_agree)
    CheckBox cbAgree;
    @BindView(R.id.tv_privacy)
    TextView tvPrivacy;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        pageName="page-login";
    }

    @Override
    public void addListeners() {

    }

    @Override
    public void requestOnCreate() {

    }

    @Override
    public void destroy() {

    }

    @OnClick({R.id.btn_login, R.id.tv_privacy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (TextUtils.isEmpty(tvCode.getText())){
                    String phone=etPhone.getText().toString();
                    if (TextUtils.isEmpty(phone)){
                        //请输入手机号
                        showToast(getResources().getString(R.string.invalid_phone_number));
                        return;
                    }else if(!phone.startsWith("8")||phone.length()<9){
                        //手机格式不正确
                        showToast(getResources().getString(R.string.invalid_phone_number));
                        return;
                    }
                    getCode();
                    return;
                }
                if (!cbAgree.isChecked()){
                    //先阅读并同意隐私协议
                    showToast(getResources().getString(R.string.have_not_check_privacy));
                    return;
                }
                toLogin();
                break;
            case R.id.tv_privacy:
                //跳转隐私协议
                startActivity(new Intent(mBaseActivity, PrivacyActivity.class));
                break;
        }
    }

    private void getCode(){
        String code=(int)(Math.random()*9000+1000)+"";
        tvCode.setText(code);
        btnLogin.setText(getResources().getString(R.string.enter));
        KeyboardUtil.hideIme(mBaseActivity,etPhone);
    }

    private void toLogin(){
        List<List<String>> list=new ArrayList<>();
        List<String> data=new ArrayList<>();
        data.add("401");
        data.add(etPhone.getText().toString());
        list.add(data);
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(list));
        Observable<BaseResult> observable=HttpUtil.createService(Interface.class).login(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {
                SharedPreferencesUtil.saveData("phone",etPhone.getText().toString());
                startActivity(new Intent(mBaseActivity,MainActivity.class));
                finish();
            }

            @Override
            public void failure(String code, Throwable throwable) {
                showToast(throwable.getMessage());
            }
        });
    }
}
