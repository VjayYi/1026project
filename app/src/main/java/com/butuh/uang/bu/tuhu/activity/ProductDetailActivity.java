package com.butuh.uang.bu.tuhu.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.adapter.DetailInfoItemAdapter;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.AdditionalBean;
import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.dialog.PopSelection;
import com.butuh.uang.bu.tuhu.dialog.PopShare;
import com.butuh.uang.bu.tuhu.dialog.ZhaoHuiDialog;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.DensityUtil;
import com.butuh.uang.bu.tuhu.util.FormatUtil;
import com.butuh.uang.bu.tuhu.util.GlideUtil;
import com.butuh.uang.bu.tuhu.util.NumberUtil;
import com.butuh.uang.bu.tuhu.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
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
    private String[] titles=new String[]{"Proses Peminjaman","Syarat Pengajuan Pinjaman"};
    private List<View> viewList = new ArrayList<>();
    private ViewPagerAd pagerAd;
    private boolean isDownloadClick=false;

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
        for (String t:titles) {
            //为TabLayout添加10个tab并设置上文本
            tabLayout.addTab(tabLayout.newTab().setText(t));
        }
        pagerAd=new ViewPagerAd();
        viewpager.setAdapter(pagerAd);
        tabLayout.setupWithViewPager(viewpager);
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
                isDownloadClick=true;
                //todo...
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
        if (isDownloadClick){
            isDownloadClick=false;
            getZhaoHuiList();
        }
    }

    private class ViewPagerAd extends PagerAdapter {

        public ViewPagerAd() {
            viewList.clear();
            for (int i = 0; i < titles.length; i++) {
                if (i == 0) {
                    View view = View.inflate(mBaseActivity, R.layout.layout_detail_view1, null);
                    viewList.add(view);
                } else {
                    //商家信息
                    View view = View.inflate(mBaseActivity, R.layout.layout_detail_view2, null);
                    viewList.add(view);
                }
            }
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//            super.destroyItem(container, position, object);
            ((ViewPager) container).removeView(viewList.get(position));
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull View container, int position) {
            View x = viewList.get(position);
            ((ViewPager) container).addView(x, 0);
            return viewList.get(position);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

    }

    private void showPopSelection1(View view){
        if (popSelection1==null){
            popSelection1=new PopSelection(mBaseActivity);
            popSelection1.setNeedFormat(true);
        }
        popSelection1.setListener(new PopSelection.OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                popSelection1.dismiss();
                tvSelection1.setText(FormatUtil.formatMoneyNoUnit(data));
                counterResult();
            }
        });
        if (popSelection1.isShowing()) {
            popSelection1.dismiss();
        } else {
            popSelection1.showAsDropDown(view, 0, 0);
            List<String> data=new ArrayList<>();
            if (mData.getDisplayTheDefaultValue().equals(mData.getShowMinimumAmount())&&mData.getShowMinimumAmount().equals(mData.getDisplayLargestAmountOfMoney())){
                //如果三个相等添加一个
                data.add(mData.getDisplayTheDefaultValue());
            }else{
                //如果默认等于最大最小其中一个显示2个
                data.add(mData.getShowMinimumAmount());
                if (!mData.getDisplayTheDefaultValue().equals(mData.getShowMinimumAmount())&&!mData.getDisplayTheDefaultValue().equals(mData.getDisplayLargestAmountOfMoney())){
                    data.add(mData.getDisplayTheDefaultValue());
                }
                data.add(mData.getDisplayLargestAmountOfMoney());
            }
            popSelection1.setData(data);
        }
    }


    private void showPopSelection2(View view){
        if (popSelection2==null){
            popSelection2=new PopSelection(mBaseActivity);
        }
        popSelection2.setListener(new PopSelection.OnItemClickListener() {
            @Override
            public void onItemClick(String data) {
                popSelection2.dismiss();
                tvSelection2.setText(data);
                counterResult();
            }
        });
        if (popSelection2.isShowing()) {
            popSelection2.dismiss();
        } else {
            popSelection2.showAsDropDown(view, 0, 0);
            List<String> data=new ArrayList<>();
            if (mData.getShowTheDefaultCycle().equals(mData.getTampilkanPeriodeMinimum())&&mData.getTampilkanPeriodeMinimum().equals(mData.getDisplayMaximumCycle())){
                //如果三个相等添加一个
                data.add(mData.getShowTheDefaultCycle());
            }else{
                //如果默认等于最大最小其中一个显示2个
                data.add(mData.getTampilkanPeriodeMinimum());
                if (!mData.getShowTheDefaultCycle().equals(mData.getTampilkanPeriodeMinimum())&&!mData.getShowTheDefaultCycle().equals(mData.getDisplayMaximumCycle())){
                    data.add(mData.getShowTheDefaultCycle());
                }
                data.add(mData.getDisplayMaximumCycle());
            }
            popSelection2.setData(data);
        }
    }

    /**
     * 利息+服务费： 利率*贷款金额*贷款期限+服务费*贷款金额
     * 还款总额：贷款金额+利息+服务费
     */
    private void counterResult(){
        int amount=NumberUtil.pasrInt(tvSelection1.getText().toString().replace(".","").replace(",","."));
        int cycle=NumberUtil.pasrInt(tvSelection2.getText().toString());
        float lixi=NumberUtil.pasrFloat(mData.getShowLoanCycle())*amount*cycle;
        float serviceFee=amount*NumberUtil.pasrFloat(mData.getRevealCoverCharge());
        String data1= FormatUtil.formatMoneyNoUnit(NumberUtil.removeInvalidateZero(lixi+serviceFee,2));
        tvDataLeft.setText(data1);

        String data2= FormatUtil.formatMoneyNoUnit(NumberUtil.removeInvalidateZero(amount+lixi+serviceFee,2));
        tvDataRight.setText(data2);
    }

    private void setData(){
        if (mData==null)
            return;
        GlideUtil.loadImage(mBaseActivity,mData.getCharacteristic(),logo);
        tvName.setText(mData.getDesignation());
        tvDescription.setText(mData.getTagline());
        tvSelection1.setText(FormatUtil.formatMoneyNoUnit(mData.getDisplayTheDefaultValue()));
        tvSelection2.setText(mData.getShowTheDefaultCycle());

        counterResult();

        for (AdditionalBean ad:mData.getAdditional()){
            if (ad.getDesignation().equals("Syarat Pengajuan Pinjaman")){
                View view=viewList.get(1);
                RecyclerView recyclerView=view.findViewById(R.id.rv_data);
                recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
                DetailInfoItemAdapter adapter=new DetailInfoItemAdapter();
                recyclerView.setAdapter(adapter);
                adapter.setNewData(ad.getParticulars());
            }else if (ad.getDesignation().equals("Proses pinjaman")){
                View view=viewList.get(0);
                RecyclerView recyclerView=view.findViewById(R.id.rv_data);
                recyclerView.setLayoutManager(new LinearLayoutManager(mBaseActivity));
                DetailInfoItemAdapter adapter=new DetailInfoItemAdapter();
                recyclerView.setAdapter(adapter);
                adapter.setNewData(ad.getParticulars());
            }
        }
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
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), "[[\"391\",\"good\"],[\"396\",\"1\"],[\"397\",\"3\"]]");
        Observable<BaseResult<PageTableBean<ProductBean>>> observable= HttpUtil.createService(Interface.class).getProductList(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback<PageTableBean<ProductBean>>() {
            @Override
            public void success(PageTableBean<ProductBean> result, String message) {
                if (result==null||result.getKuantitas()==null||result.getKuantitas().size()==0)
                    return;
                if (zhaoHuiDialog==null)
                    zhaoHuiDialog=new ZhaoHuiDialog(mBaseActivity);
                zhaoHuiDialog.show();
                zhaoHuiDialog.setData(result.getKuantitas());
            }

            @Override
            public void failure(String code, Throwable throwable) {
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });

    }

}
