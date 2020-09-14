package com.butuh.uang.bu.tuhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.adapter.DownloadListAdapter;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.SharedPreferencesUtil;
import com.butuh.uang.bu.tuhu.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class DownloadHistoryActivity extends BaseActivity {

    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.rv_data)
    RecyclerView rvData;

    private DownloadListAdapter adapter;
    private int page=1;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_download_history;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        adapter=new DownloadListAdapter();
        rvData.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        rvData.setAdapter(adapter);
    }

    @Override
    public void addListeners() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter a, View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_open:

                        break;
                }
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter a, View view, int position) {
                startActivity(new Intent(mBaseActivity, ProductDetailActivity.class).putExtra("DataID",adapter.getItem(position).getSerialNumber()));
            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                loadData();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                loadData();
            }
        });
    }

    @Override
    public void requestOnCreate() {
        loadData();
    }

    @Override
    public void destroy() {

    }

    @OnClick(R.id.share)
    public void onViewClicked() {
        showPopShare(share);
    }

    private void loadData(){
//        List<ProductBean> list= SharedPreferencesUtil.getHistory();
//        adapter.setNewData(list);
        String param="[[\"393\",\"2\"],[\"396\",\""+page+"\"],[\"397\",\"10\"]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<BaseResult<PageTableBean<ProductBean>>> observable= HttpUtil.createService(Interface.class).getProductList(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback<PageTableBean<ProductBean>>() {
            @Override
            public void success(PageTableBean<ProductBean> result, String message) {
                if(mRefreshLayout==null)
                    return;
                if(page==1)
                    mRefreshLayout.finishRefresh();
                else
                    mRefreshLayout.finishLoadMore();
                mRefreshLayout.setEnableLoadMore(result.haveMore());
                if (adapter.getEmptyViewCount()==0){
                    adapter.setEmptyView(View.inflate(mBaseActivity,R.layout.empty_no_product,null));
                }
                if (page==1)
                    adapter.setNewData(result.getKuantitas());
                else
                    adapter.addData(result.getKuantitas());
            }

            @Override
            public void failure(String code, Throwable throwable) {
                if(page==1)
                    mRefreshLayout.finishRefresh();
                else
                    mRefreshLayout.finishLoadMore();
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });
    }
}
