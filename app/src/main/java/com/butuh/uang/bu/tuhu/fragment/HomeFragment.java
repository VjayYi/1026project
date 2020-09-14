package com.butuh.uang.bu.tuhu.fragment;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.activity.LoginActivity;
import com.butuh.uang.bu.tuhu.activity.ProductDetailActivity;
import com.butuh.uang.bu.tuhu.adapter.ProductListAdapter;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.base.BaseFragment;
import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.event.CancelCollectionEvent;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.FormatUtil;
import com.butuh.uang.bu.tuhu.util.GoogleDownloadEvent;
import com.butuh.uang.bu.tuhu.util.RecyclerviewUtil;
import com.butuh.uang.bu.tuhu.util.SharedPreferencesUtil;
import com.butuh.uang.bu.tuhu.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.share)
    ImageView ivShare;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.smart)
    SmartRefreshLayout mRefreshLayout;

    @BindView(R.id.testText)
    ImageView testText;
    @BindView(R.id.testButton)
    ImageView testButton;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.text_limit)
    TextView tvLimit;

    private ProductListAdapter adapter;
    private View footer;
    private View noNetView;
    private boolean isFirstLoad=true;
    private int page = 1;


    @Override
    protected int layoutResource() {
        return R.layout.fragment_home;
    }

    @Override
    public void getIntentData() {

    }

    @Override
    public void initViews(View view) {
        pageName="page-home";
        EventBus.getDefault().register(this);
        initRefreshLayout();
        noNetView=View.inflate(mBaseActivity,R.layout.layout_no_net,null);
        footer=View.inflate(mBaseActivity,R.layout.footer_home,null);
        rvData.setLayoutManager(new LinearLayoutManager(mBaseActivity));

        adapter = new ProductListAdapter();
        adapter.setHeaderAndEmpty(true);
        adapter.setFooterView(footer);
        adapter.setEnableLoadMore(false);
        rvData.setAdapter(adapter);

        String testAmount = SharedPreferencesUtil.getStringData("testAmount");
        if (!TextUtils.isEmpty(testAmount)) {
            setTestAmount(testAmount);
        } else {
            showMoney(false);
        }

    }

    @Override
    public void addListeners() {
        noNetView.findViewById(R.id.tv_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
        footer.findViewById(R.id.iv_top).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvData.scrollToPosition(0);
            }
        });
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ProjectApplication.getInstance().isUserLogin()) {
                    //测试
                    createTestAmount();
                } else {
                    startActivity(new Intent(mBaseActivity, LoginActivity.class));
                }
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter a, View view, int position) {
                ProductBean bean=adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.iv_collection:
                        if (ProjectApplication.getInstance().isUserLogin()) {
                            collection(bean, position, bean.getFavorited());
                        } else{
                            startActivity(new Intent(mBaseActivity, LoginActivity.class));
                        }
                        break;
                    case R.id.quick_loan:
                        new GoogleDownloadEvent().openOrDownload(mBaseActivity,bean);
                        //打开应用
                        //否则下载
                        ProductBean item = adapter.getItem(position);
                        if (item == null || getActivity() == null) {
                            return;
                        }
                        AppInfoUtil.openOrInstallApp(getActivity(), item.getPacket());
                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                //SharedPreferencesUtil.saveHistory(adapter.getItem(position));
                uploadClickEvent(position);
                startActivity(new Intent(mBaseActivity, ProductDetailActivity.class).putExtra("DataID", adapter.getItem(position).getSerialNumber()));
            }
        });

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                isFirstLoad=false;
                page = 1;
                loadData();
            }
        });
        /*adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                isFirstLoad=false;
                page++;
                loadData();
            }
        });*/

    }

    @Override
    public void requestOnViewCreated() {
        loadData();
    }

    @Override
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.share)
    public void onViewClicked() {
        showPopShare(ivShare);
    }


    /**
     * 初始化SmartRefreshLayout
     * 默认配置：
     * 禁止加载更多
     * 允许自动加载更多
     * 允许下拉刷新
     * 允许子视图响应滑动
     * 允许加载时的列表滑动操作
     */
    private void initRefreshLayout() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setEnableLoadMore(false);
            mRefreshLayout.setEnableAutoLoadMore(true);
            mRefreshLayout.setEnableScrollContentWhenRefreshed(true);
            mRefreshLayout.setNestedScrollingEnabled(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateCollectionEvent(CancelCollectionEvent event) {
        if (event == null || TextUtils.isEmpty(event.getSerialNumber()))
            return;
        if (adapter == null)
            return;
        for (int i = 0; i < adapter.getData().size(); i++) {
            ProductBean p = adapter.getItem(i);
            if (p.getSerialNumber().equals(event.getSerialNumber())) {
                p.setFavorited(0);
                adapter.notifyItemChanged(i);
            }
        }
    }

    public void setTestAmount(String data) {
        showMoney(true);
        tvMoney.setText(data);
    }

    public void createTestAmount() {
        int code = (int) (Math.random() * 1000000 + 1000000);
        String money = FormatUtil.formatMoney(code);
        SharedPreferencesUtil.saveData("testAmount", money);
        setTestAmount(money);
    }

    public void showMoney(boolean b) {
        if (b) {
            testButton.setVisibility(View.GONE);
            testText.setVisibility(View.GONE);
            tvMoney.setVisibility(View.VISIBLE);
            tvLimit.setVisibility(View.VISIBLE);
        } else {
            testButton.setVisibility(View.VISIBLE);
            testText.setVisibility(View.VISIBLE);
            tvMoney.setVisibility(View.GONE);
            tvLimit.setVisibility(View.GONE);
        }
    }


    /**
     * 加载数据
     */
    private void loadData(){
        if (isFirstLoad)
            showLoadingDialog(true);
        List<List<String>> list=new ArrayList<>();
        List<String> data=new ArrayList<>();
        data.add("396");
        data.add(page + "");
        list.add(data);
        /*List<String> data1=new ArrayList<>();
        data1.add("397");
        data1.add(10+"");
        list.add(data1);*/
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(list));
        Observable<BaseResult<PageTableBean<ProductBean>>> observable = HttpUtil.createService(Interface.class).getProductList(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback<PageTableBean<ProductBean>>() {
            @Override
            public void success(PageTableBean<ProductBean> result, String message) {
                if (isFirstLoad)
                    showLoadingDialog(false);
                else{
                    if(mRefreshLayout==null)
                        return;
                    if(page==1)
                        mRefreshLayout.finishRefresh();
                    else
                        adapter.loadMoreComplete();
                }
                if (adapter==null)
                    return;
//                adapter.setEnableLoadMore(result.haveMore());
                footer.setVisibility(result.haveMore()?View.GONE:View.VISIBLE);
                /*if (adapter.getEmptyViewCount()==0){
                }*/
                adapter.setEmptyView(View.inflate(mBaseActivity,R.layout.empty_no_product,null));
                if (page==1)
                    adapter.setNewData(result.getKuantitas());
                else
                    adapter.addData(result.getKuantitas());

                if (page==1&&!isUploadEvent){
                    rvData.post(new Runnable() {
                        @Override
                        public void run() {
                            homeLoadCompleted();
                        }
                    });
                }
            }

            @Override
            public void failure(String code, Throwable throwable) {
                if (isFirstLoad)
                    showLoadingDialog(false);
                else{
                    if(mRefreshLayout==null)
                        return;
                    if(page==1)
                        mRefreshLayout.finishRefresh();
                    else
                        adapter.loadMoreComplete();
                }
                if (code.equals("99")){
                    adapter.setEmptyView(noNetView);
                }else{
                    ToastUtil.showShortToast(throwable.getMessage());
                }
            }
        });
    }

    /**
     * 点击事件上传
     * @param position
     */
    private void uploadClickEvent(int position){
        ProductBean bean=adapter.getItem(position);
        String uuid=UUID.randomUUID().toString();
        long clickTime=new Date().getTime();
        if (bean.isInstalled()){
            //应用已安装 上传应用被安装事件
            uploadInstalledEvent(bean,uuid,clickTime);
        }
        String param="[[\"reconstruct\",\"[\\\""+ uuid +"\\\", \\\"reconstruct\\\", \\\"product_list\\\", \\\""+position+"\\\"]\","+clickTime +",\""+bean.getSerialNumber()+"\"]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<BaseResult> observable = HttpUtil.createService(Interface.class).happenlog(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {

            }

            @Override
            public void failure(String code, Throwable throwable) {
            }
        });
    }

    /**
     * 应用被安装事件
     * @param bean
     * @param uuid
     * @param clickTime
     */
    private void uploadInstalledEvent(ProductBean bean,String uuid,long clickTime){
        HashMap<String,Object> map=getPkgInfos(bean.getPacket());
        //["转化点击UUID", 点击时间(int), "App名称", "包名", "版本名称", 版本号代码(int), 是否是系统应用(0不是，1是)(int), 安装时间(int), 最后更新时间(int), flags(int)]
        String param=
                "[[\"swear-in\"," +
                        "\"[\""+uuid+"\", "+clickTime+", \""+bean.getDesignation()+"\", \""+bean.getPacket()+"\", " +
                        "\""+map.get("vName")+"\", "+map.get("vCode")+", "+map.get("system")+", "+map.get("fTime")+", "+map.get("upTime")+","+map.get("flags")+"]\"," +
                        ""+new Date().getTime() +",\""+bean.getSerialNumber()+"\"]" +
                        "]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<BaseResult> observable= HttpUtil.createService(Interface.class).happenlog(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {

            }

            @Override
            public void failure(String code, Throwable throwable) {
            }
        });
    }

    /**
     * 获取应用信息
     * @param pkg
     * @return
     */
    private HashMap<String, Object> getPkgInfos(String pkg){
        HashMap<String, Object> map=new HashMap<>();
        PackageManager pckMan = mBaseActivity.getPackageManager();
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        List<PackageInfo> packageInfo = pckMan.getInstalledPackages(0);
        for (PackageInfo pInfo : packageInfo) {
            if (pInfo!=null&&pInfo.packageName.equals(pkg)){
                map.put("vName",pInfo.versionName);
                map.put("vCode",pInfo.versionCode);
                map.put("flags",pInfo.applicationInfo.flags);
                map.put("system",pInfo.applicationInfo.flags== ApplicationInfo.FLAG_SYSTEM?1:0);
                map.put("fTime",pInfo.firstInstallTime);
                map.put("upTime",pInfo.lastUpdateTime);
            }
        }
        return map;
    }

    private boolean isUploadEvent=false;

    /**
     * 首页加载完成事件
     */
    private void homeLoadCompleted(){
        //["列表区域高度"、"接口返回产品的总数量"、"列表实际显示产品总数量"]
        String param="[[\"finish-line\",\"[\\\""+rvData.getHeight()+"\\\",\\\""+adapter.getItemCount()+"\\\",\\\""+RecyclerviewUtil.getVisibleItemCount(rvData)+"\\\"]\","+new Date().getTime()+",0]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<BaseResult> observable= HttpUtil.createService(Interface.class).happenlog(body);
        HttpUtil.httpCallback(ProjectApplication.getInstance(), observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {
                isUploadEvent=true;
            }

            @Override
            public void failure(String code, Throwable throwable) {
            }
        });
    }

    private void collection(ProductBean bean, int position, int type) {
        List<List<String>> list = new ArrayList<>();
        List<String> data = new ArrayList<>();
        if (type == 0) {
            //收藏  398
            data.add("398");
        } else {
            //取消收藏   399
            data.add("399");
        }
        data.add(bean.getSerialNumber());
        list.add(data);
        showLoadingDialog(true);
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(list));
        Observable<BaseResult> observable = HttpUtil.createService(Interface.class).collection(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {
                showLoadingDialog(false);
                bean.setFavorited(type == 0 ? 1 : 0);
                adapter.notifyItemChanged(position + adapter.getHeaderLayoutCount());
            }

            @Override
            public void failure(String code, Throwable throwable) {
                showLoadingDialog(false);
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });
    }


}
