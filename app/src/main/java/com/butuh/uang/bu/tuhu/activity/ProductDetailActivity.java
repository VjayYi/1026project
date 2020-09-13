package com.butuh.uang.bu.tuhu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.dialog.PopSelection;
import com.butuh.uang.bu.tuhu.dialog.PopShare;
import com.butuh.uang.bu.tuhu.dialog.ZhaoHuiDialog;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.DensityUtil;
import com.butuh.uang.bu.tuhu.util.GlideUtil;
import com.butuh.uang.bu.tuhu.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class ProductDetailActivity extends BaseActivity {

    @BindView(R.id.logo)
    ImageView logo;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    @BindView(R.id.tv_selection1)
    TextView tvSelection1;
    @BindView(R.id.tv_selection2)
    TextView tvSelection2;
    @BindView(R.id.tv_data_left)
    TextView tvDataLeft;
    @BindView(R.id.tv_data_right)
    TextView tvDataRight;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.tv_download)
    TextView tvDownload;

    private String dataID;
    private ProductBean mData;
    private PopSelection popSelection1;
    private PopSelection popSelection2;
    private ZhaoHuiDialog zhaoHuiDialog;

    @Override
    public void getIntentData() {
        dataID = getIntent().getStringExtra("DataID");
    }

    @Override
    public int layoutResource() {
        return R.layout.activity_product_detail;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {

    }

    @Override
    public void addListeners() {

    }

    @OnClick({R.id.tv_selection1, R.id.tv_selection2, R.id.tv_download})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_selection1:
                showPopSelection1(view);
                break;
            case R.id.tv_selection2:
                showPopSelection2(view);
                break;
            case R.id.tv_download:
                break;
        }
    }

    @Override
    public void requestOnCreate() {
        loadData();
    }

    @Override
    public void destroy() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        getZhaoHuiList();
    }

    private void showPopSelection1(View view){
        if (popSelection1==null){
            popSelection1=new PopSelection(mBaseActivity);
        }
        popSelection1.setListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        if (popSelection1.isShowing()) {
            popSelection1.dismiss();
        } else {
            popSelection1.showAsDropDown(view, 0, 0);
            List<String> data=new ArrayList<>();
            data.add("8000000");
            data.add("8000000");
            data.add("8000000");
            popSelection1.setData(data);
        }
    }


    private void showPopSelection2(View view){
        if (popSelection2==null){
            popSelection2=new PopSelection(mBaseActivity);
        }
        popSelection2.setListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        if (popSelection2.isShowing()) {
            popSelection2.dismiss();
        } else {
            popSelection2.showAsDropDown(view, 0, 0);
            List<String> data=new ArrayList<>();
            data.add("8000000");
            data.add("8000000");
            data.add("8000000");
            popSelection2.setData(data);
        }
    }

    private void setData(){
        if (mData==null)
            return;
        GlideUtil.loadImage(mBaseActivity,mData.getCharacteristic(),logo);
        tvName.setText(mData.getDesignation());
        tvDescription.setText(mData.getTagline());
    }

    private void loadData() {
        List<String> data = new ArrayList<>();
        data.add(dataID);
        showLoadingDialog(true);
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(data));
        Observable<BaseResult<List<ProductBean>>> observable = HttpUtil.createService(Interface.class).productDetail(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback<List<ProductBean>>() {
            @Override
            public void success(List<ProductBean> result, String message) {
                showLoadingDialog(false);
                if (result==null||result.size()==0){
                    return;
                }
                mData=result.get(0);
                setData();
            }

            @Override
            public void failure(String code, Throwable throwable) {
                showLoadingDialog(false);
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });
    }

    private void getZhaoHuiList(){
        if (zhaoHuiDialog==null)
            zhaoHuiDialog=new ZhaoHuiDialog(mBaseActivity);
        zhaoHuiDialog.show();
    }

}
