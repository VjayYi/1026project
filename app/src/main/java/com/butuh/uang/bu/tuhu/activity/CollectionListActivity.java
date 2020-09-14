package com.butuh.uang.bu.tuhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.adapter.CollectionListAdapter;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.PageTableBean;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.event.CancelCollectionEvent;
import com.butuh.uang.bu.tuhu.http.HttpCallback;
import com.butuh.uang.bu.tuhu.http.HttpUtil;
import com.butuh.uang.bu.tuhu.http.Interface;
import com.butuh.uang.bu.tuhu.result.BaseResult;
import com.butuh.uang.bu.tuhu.util.ToastUtil;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

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

public class CollectionListActivity extends BaseActivity {


    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.rv_data)
    RecyclerView rvData;

    private int page=1;
    private CollectionListAdapter adapter;

    @Override
    public void getIntentData() {

    }

    @Override
    public int layoutResource() {
        return R.layout.activity_collection_list;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        pageName="page-collection-list";
        rvData.setLayoutManager(new LinearLayoutManager(mBaseActivity));
        adapter=new CollectionListAdapter();
        rvData.setAdapter(adapter);
    }

    @Override
    public void addListeners() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter a, View view, int position) {
                switch (view.getId()) {
                    case R.id.iv_collection:
                        if (ProjectApplication.getInstance().isUserLogin()){
                            ProductBean bean=adapter.getItem(position);
                            cancelCollection(position);
                        }else{
                            startActivity(new Intent(mBaseActivity, LoginActivity.class));
                        }
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

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
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
        String param="[[\"396\",1],[\"394\",\"2\"]]";
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<BaseResult<PageTableBean<ProductBean>>> observable= HttpUtil.createService(Interface.class).getProductList(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback<PageTableBean<ProductBean>>() {
            @Override
            public void success(PageTableBean<ProductBean> result, String message) {
                if(mRefreshLayout==null)
                    return;
                if(page==1)
                    mRefreshLayout.finishRefresh();
//                mRefreshLayout.setEnableLoadMore(result.haveMore());
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
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });
    }

    private void cancelCollection(int position){
        List<List<String>> list=new ArrayList<>();
        List<String> data=new ArrayList<>();
        //取消收藏   399
        data.add("399");
        data.add(adapter.getItem(position).getSerialNumber());
        list.add(data);
        showLoadingDialog(true);
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(list));
        Observable<BaseResult> observable= HttpUtil.createService(Interface.class).collection(body);
        HttpUtil.httpCallback(mBaseActivity, observable, new HttpCallback() {
            @Override
            public void success(Object result, String message) {
                showLoadingDialog(false);
                EventBus.getDefault().post(new CancelCollectionEvent(adapter.getItem(position).getSerialNumber()));
                adapter.remove(position);
            }

            @Override
            public void failure(String code, Throwable throwable) {
                showLoadingDialog(false);
                ToastUtil.showShortToast(throwable.getMessage());
            }
        });
    }
}
