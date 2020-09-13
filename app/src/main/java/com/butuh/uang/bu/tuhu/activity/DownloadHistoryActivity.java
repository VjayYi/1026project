package com.butuh.uang.bu.tuhu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.butuh.uang.bu.tuhu.R;
import com.butuh.uang.bu.tuhu.adapter.DownloadListAdapter;
import com.butuh.uang.bu.tuhu.app.ProjectApplication;
import com.butuh.uang.bu.tuhu.base.BaseActivity;
import com.butuh.uang.bu.tuhu.bean.ProductBean;
import com.butuh.uang.bu.tuhu.util.SharedPreferencesUtil;
import com.chad.library.afresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartredapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DownloadHistoryActivity extends BaseActivity {

    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.rv_data)
    RecyclerView rvData;

    private DownloadListAdapter adapter;

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

        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
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
        List<ProductBean> list= SharedPreferencesUtil.getHistory();
        adapter.setNewData(list);
    }
}
